package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.WeeklyReportEmail;
import com.na7ki.backend.domain.exercise.entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.CaseInfoData;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.exercise_management.assignment.AssignmentService;
import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import com.na7ki.backend.exercise_management.assignment.repository.AssignmentRepository;
import com.na7ki.backend.notification.NotificationService;
import com.na7ki.backend.specialist_actions.report.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeeklyReportService {

    private final SpecialistRepository specialistRepository;
    private final TaskResultRepository taskResultRepository;

    private final AssignmentService assignmentService;
    private final EmailService emailService;
    private final NotificationService notificationService;

    private final ReportStatsSupport stats;

    private static final DateTimeFormatter WEEK_DATE_FMT =
            DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);
    private static final DateTimeFormatter WEEK_YEAR_FMT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    // Scheduled job
    @Transactional(readOnly = true)
    public void sendWeeklyReports() {
        WeekWindow w = weekWindow();
        for (Specialist specialist : specialistRepository.findAll()) {
            try {
                WeeklyReportResponse report = buildReport(specialist, w);
                if (report.getPatients().isEmpty()) continue;

                List<PatientWeeklyReport> flagged = report.getPatients().stream()
                        .filter(p -> !p.getAttentionReasons().isEmpty()).toList();
                if (!flagged.isEmpty()) {
                    emailService.sendWeeklyReport(
                            specialist.getEmail(),
                            new WeeklyReportEmail(specialist.getName(), report.getWeekLabel(), toEmailBody(flagged))
                    );
                    log.info("Weekly report sent to: {}", specialist.getEmail());
                }

                notificationService.notifyReportReady(specialist, "Weekly", report.getWeekLabel());
            } catch (Exception e) {
                log.error("Failed to send weekly report to {}: {}", specialist.getEmail(), e.getMessage());
            }
        }
    }

    // On-demand for authenticated specialist. Re-fetched by id since the principal attached by the
    // JWT filter may be detached by the time this runs, and `patients` is a lazy collection.
    @Transactional(readOnly = true)
    public WeeklyReportResponse getReportForSpecialist(Specialist specialist) {
        Specialist fresh = specialistRepository.findById(specialist.getUserId()).orElseThrow();
        return buildReport(fresh, weekWindow());
    }

    // Core builder
    private WeeklyReportResponse buildReport(Specialist specialist, WeekWindow w) {
        List<Patient> patients = specialist.getPatients();
        List<PatientWeeklyReport> patientReports = new ArrayList<>();
        if (patients != null) {
            for (Patient p : patients) {
                PatientWeeklyReport pr = buildPatientReport(p, w);
                if (pr != null) patientReports.add(pr);
            }
        }
        return WeeklyReportResponse.builder()
                .weekLabel(w.label())
                .generatedAt(OffsetDateTime.now())
                .patients(patientReports)
                .build();
    }

    private PatientWeeklyReport buildPatientReport(Patient patient, WeekWindow w) {
        List<TaskResult> thisWeek = taskResultRepository
                .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), w.from(), w.to());
        List<TaskResult> lastWeek = taskResultRepository
                .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), w.prevFrom(), w.prevTo());

        if (thisWeek.isEmpty() && lastWeek.isEmpty()) return null;

        long activeDays = thisWeek.stream().map(r -> r.getStartedAt().toLocalDate()).distinct().count();
        int streak = calcStreak(thisWeek);
        int possibleActiveDays = possibleActiveDays(patient, w);

        // Keyed by (exerciseType, taskId) — grouping by taskName alone risked merging a cognitive
        // task and a practice package that happen to share a display title.
        Map<ReportStatsSupport.TaskGroupKey, List<TaskResult>> thisWeekByTask = thisWeek.stream()
                .collect(Collectors.groupingBy(r -> new ReportStatsSupport.TaskGroupKey(r.getExerciseType(), r.getTaskId())));
        Map<ReportStatsSupport.TaskGroupKey, List<TaskResult>> lastWeekByTask = lastWeek.stream()
                .collect(Collectors.groupingBy(r -> new ReportStatsSupport.TaskGroupKey(r.getExerciseType(), r.getTaskId())));

        List<TaskWeeklyStats> taskStatsList = new ArrayList<>();
        Map<String, Double> accByCognitiveTask = new LinkedHashMap<>();
        Map<String, Double> accByPracticePackage = new LinkedHashMap<>();
        for (Map.Entry<ReportStatsSupport.TaskGroupKey, List<TaskResult>> e : thisWeekByTask.entrySet()) {
            ReportStatsSupport.TaskGroupKey key = e.getKey();
            String name = e.getValue().get(0).getTaskName();
            TaskWeeklyStats ts = buildTaskStats(name, key.exerciseType(), e.getValue(),
                    lastWeekByTask.getOrDefault(key, List.of()));
            taskStatsList.add(ts);
            if (ts.getAvgAccuracy() != null) {
                if (key.exerciseType() == ExerciseType.TASK) accByCognitiveTask.put(name, ts.getAvgAccuracy());
                else accByPracticePackage.put(name, ts.getAvgAccuracy());
            }
        }

        String bestCognitiveTask   = stats.bestOf(accByCognitiveTask);
        String worstCognitiveTask  = stats.worstOf(accByCognitiveTask);
        String bestPracticePackage = stats.bestOf(accByPracticePackage);
        String worstPracticePackage = stats.worstOf(accByPracticePackage);

        // Error taxonomies differ between cognitive tasks and practice packages, so errors are
        // tallied per category rather than merged into one leaderboard.
        Map<String, Integer> cognitiveErrors = new LinkedHashMap<>();
        Map<String, Integer> practiceErrors = new LinkedHashMap<>();
        thisWeek.forEach(r -> {
            if (r.getErrorBreakdown() == null) return;
            Map<String, Integer> target = r.getExerciseType() == ExerciseType.TASK ? cognitiveErrors : practiceErrors;
            r.getErrorBreakdown().forEach((k, v) -> target.merge(k, v, Integer::sum));
        });
        Map<String, Integer> notableCognitiveErrors = stats.topN(cognitiveErrors, 3);
        Map<String, Integer> notablePracticeErrors = stats.topN(practiceErrors, 3);

        // Keyed by (exerciseType, id) since Task ids and Package ids are independent sequences and can collide.
        Set<String> attemptedKeys = thisWeek.stream()
                .map(r -> r.getExerciseType() + ":" + r.getTaskId())
                .collect(Collectors.toSet());
        List<String> skipped = assignmentService.getAssignmentsByPatient(patient).stream()
                .flatMap(a -> a.getAssignedExercises().stream())
                .filter(ex -> (ex.getType() == ExerciseType.TASK && ex.getTask() != null)
                        || (ex.getType() == ExerciseType.QUESTION && ex.getQuestion() != null))
                .filter(ex -> {
                    Long id = ex.getType() == ExerciseType.TASK
                            ? ex.getTask().getId()
                            : ex.getQuestion().getPkg().getId();
                    return !attemptedKeys.contains(ex.getType() + ":" + id);
                })
                .map(ex -> ex.getType() == ExerciseType.TASK
                        ? ex.getTask().getTitle()
                        : ex.getQuestion().getPkg().getTitle())
                .distinct().collect(Collectors.toList());

        List<String> attentionReasons = attentionReasons(thisWeek, taskStatsList, skipped);

        String diagnosis = null; LocalDate tStart = null; LocalDate tEnd = null;
        if (patient.getMedicalDetails() != null
                && patient.getMedicalDetails().additionalInfoData() != null
                && patient.getMedicalDetails().additionalInfoData().caseInfoData() != null) {
            CaseInfoData ci = patient.getMedicalDetails().additionalInfoData().caseInfoData();
            diagnosis = ci.primaryDiagnosis(); tStart = ci.startDate();
        }

        return PatientWeeklyReport.builder()
                .patientName(patient.getName()).patientSpecificId(patient.getPatientID())
                .diagnosis(diagnosis).treatmentStart(tStart)
                .totalSessions(thisWeek.size()).activeDays(activeDays)
                .possibleActiveDays(possibleActiveDays).streak(streak)
                .tasks(taskStatsList)
                .bestCognitiveTask(bestCognitiveTask).worstCognitiveTask(worstCognitiveTask)
                .notableCognitiveErrors(notableCognitiveErrors)
                .bestPracticePackage(bestPracticePackage).worstPracticePackage(worstPracticePackage)
                .notablePracticeErrors(notablePracticeErrors)
                .skippedTasks(skipped).attentionReasons(attentionReasons).build();
    }

    private static final double ACCURACY_DROP_THRESHOLD = 0.15;   // 15 percentage points vs last week
    private static final double LOW_COMPLETION_THRESHOLD = 50.0;  // percent

    private List<String> attentionReasons(List<TaskResult> thisWeek, List<TaskWeeklyStats> taskStatsList,
                                           List<String> skipped) {
        List<String> reasons = new ArrayList<>();
        if (thisWeek.isEmpty()) {
            reasons.add("No sessions this week");
        }
        if (!skipped.isEmpty()) {
            reasons.add(skipped.size() + " task(s)/package(s) not attempted");
        }
        for (TaskWeeklyStats t : taskStatsList) {
            if (t.getAccuracyDiff() != null && t.getAccuracyDiff() <= -ACCURACY_DROP_THRESHOLD) {
                reasons.add("Accuracy dropped in " + t.getTaskName());
            }
            if (t.getSessions() > 0 && t.getCompletionPct() < LOW_COMPLETION_THRESHOLD) {
                reasons.add("Low completion in " + t.getTaskName());
            }
        }
        return reasons;
    }

    private TaskWeeklyStats buildTaskStats(String name, ExerciseType exerciseType, List<TaskResult> cur, List<TaskResult> prev) {
        int n = cur.size(), pn = prev.size();
        long completed = cur.stream().filter(TaskResult::isCompleted).count();

        Double avgAcc      = stats.nullableDouble(cur, r -> r.getAccuracy() != null, r -> r.getAccuracy().doubleValue());
        Double prevAvgAcc  = stats.nullableDouble(prev, r -> r.getAccuracy() != null, r -> r.getAccuracy().doubleValue());
        Double avgAtt      = stats.nullableInt(cur, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
        Double prevAvgAtt  = stats.nullableInt(prev, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
        Double avgDur      = stats.nullableInt(cur, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
        Double prevAvgDur  = stats.nullableInt(prev, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
        Double avgReact    = stats.nullableInt(cur, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);
        Double prevAvgReact= stats.nullableInt(prev, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);

        Map<String, Integer> errors = new LinkedHashMap<>();
        cur.forEach(r -> { if (r.getErrorBreakdown() != null)
            r.getErrorBreakdown().forEach((k, v) -> errors.merge(k, v, Integer::sum)); });
        Map<String, Integer> topErrors = stats.topN(errors, 3);

        return TaskWeeklyStats.builder()
                .taskName(name).exerciseType(exerciseType).sessions(n).sessionsDiff(pn > 0 ? n - pn : null)
                .completedCount(completed).completionPct(completed * 100.0 / n)
                .avgAccuracy(avgAcc).accuracyDiff(stats.diff(avgAcc, prevAvgAcc))
                .avgAttempts(avgAtt).attemptsDiff(stats.diff(avgAtt, prevAvgAtt))
                .avgDurationSeconds(avgDur).durationDiff(stats.diff(avgDur, prevAvgDur))
                .avgReactionTimeMs(avgReact).reactionDiff(stats.diff(avgReact, prevAvgReact))
                .topErrors(topErrors).extraMetrics(stats.mergeExtra(cur)).build();
    }

    // Email formatter — flagged patients only. On-track patients aren't mentioned at all here; the
    // full picture (everyone, full task-level detail) lives in the app via the API (WeeklyReportResponse).
    String toEmailBody(List<PatientWeeklyReport> flagged) {
        StringBuilder sb = new StringBuilder();
        sb.append("NEEDS ATTENTION (").append(flagged.size()).append("):\n\n");
        for (PatientWeeklyReport p : flagged) {
            appendPatientSummary(sb, p);
            p.getAttentionReasons().forEach(r -> sb.append("  ⚠ ").append(r).append("\n"));
            sb.append("\n");
        }
        sb.append("Open the Na7ki app to see full task-by-task details for every patient.\n");
        return sb.toString().trim();
    }

    private void appendPatientSummary(StringBuilder sb, PatientWeeklyReport p) {
        sb.append("=== ").append(p.getPatientName()).append(" ===\n");
        if (p.getDiagnosis() != null) sb.append("  Diagnosis: ").append(p.getDiagnosis()).append("\n");
        sb.append(String.format("  Activity: %d sessions | %d/%d days | %d-day streak\n",
                p.getTotalSessions(), p.getActiveDays(), p.getPossibleActiveDays(), p.getStreak()));
    }

    // Helpers
    private WeekWindow weekWindow() {
        LocalDate today = LocalDate.now();
        LocalDate ws = today.with(DayOfWeek.MONDAY).minusWeeks(1);
        LocalDate we = ws.plusDays(6);
        return new WeekWindow(
                ws.atStartOfDay().atOffset(ZoneOffset.UTC), we.atTime(23,59,59).atOffset(ZoneOffset.UTC),
                ws.minusWeeks(1).atStartOfDay().atOffset(ZoneOffset.UTC), we.minusWeeks(1).atTime(23,59,59).atOffset(ZoneOffset.UTC),
                ws.format(WEEK_DATE_FMT) + " – " + we.format(WEEK_YEAR_FMT));
    }

    // Clips the window to the patient's registration date so a patient who joined mid-week
    // isn't scored against days before they existed (e.g. "2/7 days" when they only had 2 possible days).
    private int possibleActiveDays(Patient patient, WeekWindow w) {
        LocalDate weekStart = w.from().toLocalDate();
        LocalDate weekEnd = w.to().toLocalDate();
        LocalDate registeredAt = patient.getCreatedAtDate().toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate effectiveStart = registeredAt.isAfter(weekStart) ? registeredAt : weekStart;
        if (effectiveStart.isAfter(weekEnd)) return 0;
        return (int) ChronoUnit.DAYS.between(effectiveStart, weekEnd) + 1;
    }

    private int calcStreak(List<TaskResult> sessions) {
        List<LocalDate> days = sessions.stream().map(r -> r.getStartedAt().toLocalDate()).distinct().sorted().toList();
        if (days.isEmpty()) return 0;
        int s = 1;
        for (int i = days.size()-1; i > 0; i--) { if (days.get(i).minusDays(1).equals(days.get(i-1))) s++; else break; }
        return s;
    }

    private record WeekWindow(OffsetDateTime from, OffsetDateTime to,
                               OffsetDateTime prevFrom, OffsetDateTime prevTo, String label) {}
}

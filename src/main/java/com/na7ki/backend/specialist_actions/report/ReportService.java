package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.MonthlyReportEmail;
import com.na7ki.backend.domain.exercise.entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.CaseInfoData;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.specialist_actions.report.dto.*;
import com.na7ki.backend.task_management.assignment.AssignmentRepository;
import com.na7ki.backend.task_management.assignment.entity.enums.ExerciseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final SpecialistRepository specialistRepository;
    private final TaskResultRepository taskResultRepository;
    private final AssignmentRepository assignmentRepository;
    private final EmailService emailService;
    private final ReportStatsSupport stats;

    // Scheduled job
    @Transactional(readOnly = true)
    public void sendMonthlyReports() {
        MonthWindow w = monthWindow();
        for (Specialist specialist : specialistRepository.findAll()) {
            try {
                MonthlyReportResponse report = buildReport(specialist, w);
                if (report.getPatients().isEmpty()) continue;
                emailService.sendMonthlyReport(
                        specialist.getEmail(),
                        new MonthlyReportEmail(specialist.getName(), report.getMonthLabel(), toEmailBody(report))
                );
                log.info("Monthly report sent to: {}", specialist.getEmail());
            } catch (Exception e) {
                log.error("Failed to send monthly report to {}: {}", specialist.getEmail(), e.getMessage());
            }
        }
    }

    // On-demand for authenticated specialist. Re-fetched by id since the principal attached by the
    // JWT filter may be detached by the time this runs, and `patients` is a lazy collection.
    @Transactional(readOnly = true)
    public MonthlyReportResponse getReportForSpecialist(Specialist specialist) {
        Specialist fresh = specialistRepository.findById(specialist.getUserId()).orElseThrow();
        return buildReport(fresh, monthWindow());
    }

    // Core builder
    private MonthlyReportResponse buildReport(Specialist specialist, MonthWindow w) {
        List<Patient> patients = specialist.getPatients();
        List<PatientMonthlyReport> patientReports = new ArrayList<>();
        if (patients != null) {
            for (Patient p : patients) {
                PatientMonthlyReport pr = buildPatientReport(p, w);
                if (pr != null) patientReports.add(pr);
            }
        }
        return MonthlyReportResponse.builder()
                .monthLabel(w.label())
                .generatedAt(OffsetDateTime.now())
                .patients(patientReports)
                .build();
    }

    private PatientMonthlyReport buildPatientReport(Patient patient, MonthWindow w) {
        List<TaskResult> thisMonth = taskResultRepository
                .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), w.from(), w.to());
        List<TaskResult> lastMonth = taskResultRepository
                .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), w.prevFrom(), w.prevTo());

        if (thisMonth.isEmpty() && lastMonth.isEmpty()) return null;

        Map<ReportStatsSupport.TaskGroupKey, List<TaskResult>> thisMonthByTask = thisMonth.stream()
                .collect(Collectors.groupingBy(r -> new ReportStatsSupport.TaskGroupKey(r.getExerciseType(), r.getTaskId())));
        Map<ReportStatsSupport.TaskGroupKey, List<TaskResult>> lastMonthByTask = lastMonth.stream()
                .collect(Collectors.groupingBy(r -> new ReportStatsSupport.TaskGroupKey(r.getExerciseType(), r.getTaskId())));

        List<TaskStats> taskStatsList = new ArrayList<>();
        Map<String, Double> accByCognitiveTask = new LinkedHashMap<>();
        Map<String, Double> accByPracticePackage = new LinkedHashMap<>();
        for (Map.Entry<ReportStatsSupport.TaskGroupKey, List<TaskResult>> e : thisMonthByTask.entrySet()) {
            ReportStatsSupport.TaskGroupKey key = e.getKey();
            String name = e.getValue().get(0).getTaskName();
            TaskStats ts = buildTaskStats(name, key.exerciseType(), e.getValue(),
                    lastMonthByTask.getOrDefault(key, List.of()));
            taskStatsList.add(ts);
            if (ts.getAvgAccuracy() != null) {
                if (key.exerciseType() == ExerciseType.TASK) accByCognitiveTask.put(name, ts.getAvgAccuracy());
                else accByPracticePackage.put(name, ts.getAvgAccuracy());
            }
        }

        String bestCognitiveTask    = stats.bestOf(accByCognitiveTask);
        String worstCognitiveTask   = stats.worstOf(accByCognitiveTask);
        String bestPracticePackage  = stats.bestOf(accByPracticePackage);
        String worstPracticePackage = stats.worstOf(accByPracticePackage);

        // Error taxonomies differ between cognitive tasks and practice packages, so errors are
        // tallied per category rather than merged into one leaderboard.
        Map<String, Integer> cognitiveErrors = new LinkedHashMap<>();
        Map<String, Integer> practiceErrors = new LinkedHashMap<>();
        thisMonth.forEach(r -> {
            if (r.getErrorBreakdown() == null) return;
            Map<String, Integer> target = r.getExerciseType() == ExerciseType.TASK ? cognitiveErrors : practiceErrors;
            r.getErrorBreakdown().forEach((k, v) -> target.merge(k, v, Integer::sum));
        });
        Map<String, Integer> notableCognitiveErrors = stats.topN(cognitiveErrors, 3);
        Map<String, Integer> notablePracticeErrors = stats.topN(practiceErrors, 3);

        // Keyed by (exerciseType, id) since Task ids and Package ids are independent sequences and can collide.
        Set<String> attemptedKeys = thisMonth.stream()
                .map(r -> r.getExerciseType() + ":" + r.getTaskId())
                .collect(Collectors.toSet());
        List<String> skipped = assignmentRepository.findByPatient(patient).stream()
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

        List<String> attentionReasons = attentionReasons(thisMonth, taskStatsList, skipped);

        String diagnosis = null; LocalDate tStart = null; LocalDate tEnd = null;
        if (patient.getMedicalDetails() != null
                && patient.getMedicalDetails().additionalInfoData() != null
                && patient.getMedicalDetails().additionalInfoData().caseInfoData() != null) {
            CaseInfoData ci = patient.getMedicalDetails().additionalInfoData().caseInfoData();
            diagnosis = ci.primaryDiagnosis(); tStart = ci.startDate(); tEnd = ci.endDate();
        }

        return PatientMonthlyReport.builder()
                .patientName(patient.getName()).patientSpecificId(patient.getPatientID())
                .diagnosis(diagnosis).treatmentStart(tStart).treatmentEnd(tEnd)
                .totalSessions(thisMonth.size())
                .tasks(taskStatsList)
                .bestCognitiveTask(bestCognitiveTask).worstCognitiveTask(worstCognitiveTask)
                .notableCognitiveErrors(notableCognitiveErrors)
                .bestPracticePackage(bestPracticePackage).worstPracticePackage(worstPracticePackage)
                .notablePracticeErrors(notablePracticeErrors)
                .skippedTasks(skipped).attentionReasons(attentionReasons).build();
    }

    private static final double ACCURACY_DROP_THRESHOLD = 0.15;   // 15 percentage points vs previous month
    private static final double LOW_COMPLETION_THRESHOLD = 50.0;  // percent

    private List<String> attentionReasons(List<TaskResult> thisMonth, List<TaskStats> taskStatsList,
                                           List<String> skipped) {
        List<String> reasons = new ArrayList<>();
        if (thisMonth.isEmpty()) {
            reasons.add("No sessions this month");
        }
        if (!skipped.isEmpty()) {
            reasons.add(skipped.size() + " task(s)/package(s) not attempted");
        }
        for (TaskStats t : taskStatsList) {
            if (t.getAccuracyDiff() != null && t.getAccuracyDiff() <= -ACCURACY_DROP_THRESHOLD) {
                reasons.add("Accuracy dropped in " + t.getTaskName());
            }
            if (t.getSessions() > 0 && t.getCompletionPct() < LOW_COMPLETION_THRESHOLD) {
                reasons.add("Low completion in " + t.getTaskName());
            }
        }
        return reasons;
    }

    private TaskStats buildTaskStats(String name, ExerciseType exerciseType, List<TaskResult> cur, List<TaskResult> prev) {
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

        return TaskStats.builder()
                .taskName(name).exerciseType(exerciseType).sessions(n).sessionsDiff(pn > 0 ? n - pn : null)
                .completedCount(completed).completionPct(completed * 100.0 / n)
                .avgAccuracy(avgAcc).accuracyDiff(stats.diff(avgAcc, prevAvgAcc))
                .avgAttempts(avgAtt).attemptsDiff(stats.diff(avgAtt, prevAvgAtt))
                .avgDurationSeconds(avgDur).durationDiff(stats.diff(avgDur, prevAvgDur))
                .avgReactionTimeMs(avgReact).reactionDiff(stats.diff(avgReact, prevAvgReact))
                .topErrors(topErrors).extraMetrics(stats.mergeExtra(cur)).build();
    }

    // Email formatter — digest only, mirroring the weekly report: no task-level breakdown ever goes
    // in the email. Every detail (per-task stats, best/worst, errors, skipped list) is only ever
    // available via the app/API (MonthlyReportResponse).
    String toEmailBody(MonthlyReportResponse report) {
        List<PatientMonthlyReport> flagged = report.getPatients().stream()
                .filter(p -> !p.getAttentionReasons().isEmpty()).toList();
        List<PatientMonthlyReport> onTrack = report.getPatients().stream()
                .filter(p -> p.getAttentionReasons().isEmpty()).toList();

        StringBuilder sb = new StringBuilder();

        if (!flagged.isEmpty()) {
            sb.append("NEEDS ATTENTION (").append(flagged.size()).append("):\n\n");
            for (PatientMonthlyReport p : flagged) {
                appendPatientSummary(sb, p);
                p.getAttentionReasons().forEach(r -> sb.append("  ⚠ ").append(r).append("\n"));
                sb.append("\n");
            }
        }

        if (!onTrack.isEmpty()) {
            sb.append("ON TRACK (").append(onTrack.size()).append("):\n\n");
            for (PatientMonthlyReport p : onTrack) {
                appendPatientSummary(sb, p);
            }
            sb.append("\n");
        }

        sb.append("Open the Na7ki app to see full task-by-task details for every patient.\n");
        return sb.toString().trim();
    }

    private void appendPatientSummary(StringBuilder sb, PatientMonthlyReport p) {
        sb.append("=== ").append(p.getPatientName()).append(" ===\n");
        if (p.getDiagnosis() != null) sb.append("  Diagnosis: ").append(p.getDiagnosis()).append("\n");
        sb.append(String.format("  Activity: %d sessions this month\n", p.getTotalSessions()));
    }

    // Helpers
    private MonthWindow monthWindow() {
        YearMonth last = YearMonth.now().minusMonths(1);
        YearMonth prev = last.minusMonths(1);
        String label = last.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
        return new MonthWindow(
                last.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC),
                last.atEndOfMonth().atTime(23,59,59).atOffset(ZoneOffset.UTC),
                prev.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC),
                prev.atEndOfMonth().atTime(23,59,59).atOffset(ZoneOffset.UTC),
                label);
    }

    private record MonthWindow(OffsetDateTime from, OffsetDateTime to,
                                OffsetDateTime prevFrom, OffsetDateTime prevTo, String label) {}
}

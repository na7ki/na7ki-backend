package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.WeeklyReportEmail;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.exercise.entity.TaskResult;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.CaseInfoData;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.specialist_actions.report.dto.*;
import com.na7ki.backend.exercise_management.assignment.AssignmentRepository;
import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeeklyReportService {

    private final SpecialistRepository specialistRepository;
    private final TaskResultRepository taskResultRepository;
    private final AssignmentRepository assignmentRepository;
    private final EmailService emailService;

    private static final DateTimeFormatter WEEK_DATE_FMT =
            DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);
    private static final DateTimeFormatter WEEK_YEAR_FMT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    // Scheduled job
    public void sendWeeklyReports() {
        WeekWindow w = weekWindow();
        for (Specialist specialist : specialistRepository.findAll()) {
            try {
                WeeklyReportResponse report = buildReport(specialist, w);
                if (report.getPatients().isEmpty()) continue;
                emailService.sendWeeklyReport(
                        specialist.getEmail(),
                        new WeeklyReportEmail(specialist.getName(), report.getWeekLabel(), toEmailBody(report))
                );
                log.info("Weekly report sent to: {}", specialist.getEmail());
            } catch (Exception e) {
                log.error("Failed to send weekly report to {}: {}", specialist.getEmail(), e.getMessage());
            }
        }
    }

    // On-demand for authenticated specialist
    public WeeklyReportResponse getReportForSpecialist(Specialist specialist) {
        return buildReport(specialist, weekWindow());
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

        Map<String, List<TaskResult>> thisWeekByTask = thisWeek.stream()
                .collect(Collectors.groupingBy(TaskResult::getTaskName));
        Map<String, List<TaskResult>> lastWeekByTask = lastWeek.stream()
                .collect(Collectors.groupingBy(TaskResult::getTaskName));

        List<TaskWeeklyStats> taskStatsList = new ArrayList<>();
        Map<String, Double> accByTask = new LinkedHashMap<>();
        for (Map.Entry<String, List<TaskResult>> e : thisWeekByTask.entrySet()) {
            TaskWeeklyStats ts = buildTaskStats(e.getKey(), e.getValue(),
                    lastWeekByTask.getOrDefault(e.getKey(), List.of()));
            taskStatsList.add(ts);
            if (ts.getAvgAccuracy() != null) accByTask.put(ts.getTaskName(), ts.getAvgAccuracy());
        }

        String bestTask  = accByTask.isEmpty() ? null :
                Collections.max(accByTask.entrySet(), Map.Entry.comparingByValue()).getKey();
        String worstTask = accByTask.size() < 2 ? null :
                Collections.min(accByTask.entrySet(), Map.Entry.comparingByValue()).getKey();

        Map<String, Integer> allErrors = new LinkedHashMap<>();
        thisWeek.forEach(r -> { if (r.getErrorBreakdown() != null)
            r.getErrorBreakdown().forEach((k, v) -> allErrors.merge(k, v, Integer::sum)); });
        Map<String, Integer> notableErrors = allErrors.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        Set<Integer> attemptedIds = thisWeek.stream().map(TaskResult::getTaskId).collect(Collectors.toSet());
        List<String> skipped = assignmentRepository.findByPatient(patient).stream()
                .flatMap(a -> a.getAssignedExercises().stream())
                .filter(ex -> ex.getType() == ExerciseType.TASK && ex.getTask() != null)
                .filter(ex -> !attemptedIds.contains(ex.getTask().getId().intValue()))
                .map(ex -> ex.getTask().getTitle()).distinct().collect(Collectors.toList());

        String diagnosis = null; LocalDate tStart = null;
        if (patient.getMedicalDetails() != null
                && patient.getMedicalDetails().additionalInfoData() != null
                && patient.getMedicalDetails().additionalInfoData().caseInfoData() != null) {
            CaseInfoData ci = patient.getMedicalDetails().additionalInfoData().caseInfoData();
            diagnosis = ci.primaryDiagnosis(); tStart = ci.startDate();
        }

        return PatientWeeklyReport.builder()
                .patientName(patient.getName()).patientSpecificId(patient.getPatientID())
                .diagnosis(diagnosis).treatmentStart(tStart)
                .totalSessions(thisWeek.size()).activeDays(activeDays).streak(streak)
                .tasks(taskStatsList).bestTask(bestTask).worstTask(worstTask)
                .notableErrors(notableErrors).skippedTasks(skipped).build();
    }

    private TaskWeeklyStats buildTaskStats(String name, List<TaskResult> cur, List<TaskResult> prev) {
        int n = cur.size(), pn = prev.size();
        long completed = cur.stream().filter(TaskResult::isCompleted).count();

        Double avgAcc      = nullableDouble(cur, r -> r.getAccuracy() != null, r -> r.getAccuracy().doubleValue());
        Double prevAvgAcc  = nullableDouble(prev, r -> r.getAccuracy() != null, r -> r.getAccuracy().doubleValue());
        Double avgAtt      = nullableInt(cur, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
        Double prevAvgAtt  = nullableInt(prev, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
        Double avgDur      = nullableInt(cur, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
        Double prevAvgDur  = nullableInt(prev, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
        Double avgReact    = nullableInt(cur, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);
        Double prevAvgReact= nullableInt(prev, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);

        Map<String, Integer> errors = new LinkedHashMap<>();
        cur.forEach(r -> { if (r.getErrorBreakdown() != null)
            r.getErrorBreakdown().forEach((k, v) -> errors.merge(k, v, Integer::sum)); });
        Map<String, Integer> topErrors = errors.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        return TaskWeeklyStats.builder()
                .taskName(name).sessions(n).sessionsDiff(pn > 0 ? n - pn : null)
                .completedCount(completed).completionPct(completed * 100.0 / n)
                .avgAccuracy(avgAcc).accuracyDiff(diff(avgAcc, prevAvgAcc))
                .avgAttempts(avgAtt).attemptsDiff(diff(avgAtt, prevAvgAtt))
                .avgDurationSeconds(avgDur).durationDiff(diff(avgDur, prevAvgDur))
                .avgReactionTimeMs(avgReact).reactionDiff(diff(avgReact, prevAvgReact))
                .topErrors(topErrors).extraMetrics(mergeExtra(cur)).build();
    }

    // Email formatter
    String toEmailBody(WeeklyReportResponse report) {
        StringBuilder sb = new StringBuilder();
        for (PatientWeeklyReport p : report.getPatients()) {
            sb.append("=== Patient: ").append(p.getPatientName()).append(" ===\n");
            if (p.getDiagnosis() != null) sb.append("  Diagnosis: ").append(p.getDiagnosis()).append("\n");
            sb.append(String.format("  Activity: %d sessions | %d/7 days | %d-day streak\n",
                    p.getTotalSessions(), p.getActiveDays(), p.getStreak()));
            if (p.getTasks().isEmpty()) { sb.append("  No sessions this week.\n\n"); continue; }
            sb.append("\n  Tasks:\n");
            for (TaskWeeklyStats t : p.getTasks()) {
                sb.append("  • ").append(t.getTaskName()).append(":\n");
                sb.append(String.format("      Sessions: %d%s | Completed: %d/%d (%.0f%%)\n",
                        t.getSessions(), t.getSessionsDiff() != null ? diffStr(t.getSessionsDiff(),"","") : "",
                        t.getCompletedCount(), t.getSessions(), t.getCompletionPct()));
                if (t.getAvgAccuracy() != null) sb.append(String.format(
                        "      Avg accuracy: %.0f%%%s\n", t.getAvgAccuracy()*100,
                        t.getAccuracyDiff() != null ? diffStr(t.getAccuracyDiff()*100,"%.0f%%"," vs last week") : ""));
                if (t.getAvgAttempts() != null) sb.append(String.format(
                        "      Avg attempts: %.1f%s\n", t.getAvgAttempts(),
                        t.getAttemptsDiff() != null ? diffStr(t.getAttemptsDiff(),"%.1f"," vs last week") : ""));
                if (t.getAvgDurationSeconds() != null) sb.append(String.format(
                        "      Avg duration: %.0fs%s\n", t.getAvgDurationSeconds(),
                        t.getDurationDiff() != null ? diffStr(t.getDurationDiff(),"%.0fs"," vs last week") : ""));
                if (t.getAvgReactionTimeMs() != null) sb.append(String.format(
                        "      Avg reaction: %.0fms%s\n", t.getAvgReactionTimeMs(),
                        t.getReactionDiff() != null ? diffStr(t.getReactionDiff(),"%.0fms"," vs last week") : ""));
                if (!t.getTopErrors().isEmpty()) {
                    sb.append("      Top errors:\n");
                    t.getTopErrors().forEach((k,v) -> sb.append("        - ").append(k).append(": ").append(v).append("\n"));
                }
                if (!t.getExtraMetrics().isEmpty()) {
                    sb.append("      Extras:\n");
                    t.getExtraMetrics().forEach((k,v) -> {
                        if (v instanceof Double d) sb.append(String.format("        - %s: %.2f\n",k,d));
                        else sb.append("        - ").append(k).append(": ").append(v).append("\n");
                    });
                }
                sb.append("\n");
            }
            if (p.getBestTask() != null) sb.append("  ★ Best:  ").append(p.getBestTask()).append("\n");
            if (p.getWorstTask() != null) sb.append("  ✗ Worst: ").append(p.getWorstTask()).append("\n");
            if (!p.getNotableErrors().isEmpty()) {
                sb.append("  Notable errors:\n");
                p.getNotableErrors().forEach((k,v) -> sb.append("    - ").append(k).append(": ").append(v).append("\n"));
            }
            if (!p.getSkippedTasks().isEmpty()) {
                sb.append("  Not attempted:\n");
                p.getSkippedTasks().forEach(t -> sb.append("    - ").append(t).append("\n"));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
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

    private int calcStreak(List<TaskResult> sessions) {
        List<LocalDate> days = sessions.stream().map(r -> r.getStartedAt().toLocalDate()).distinct().sorted().toList();
        if (days.isEmpty()) return 0;
        int s = 1;
        for (int i = days.size()-1; i > 0; i--) { if (days.get(i).minusDays(1).equals(days.get(i-1))) s++; else break; }
        return s;
    }

    private Double nullableDouble(List<TaskResult> s, Predicate<TaskResult> f, ToDoubleFunction<TaskResult> m) {
        OptionalDouble o = s.stream().filter(f).mapToDouble(m).average(); return o.isPresent() ? o.getAsDouble() : null;
    }
    private Double nullableInt(List<TaskResult> s, Predicate<TaskResult> f, ToIntFunction<TaskResult> m) {
        OptionalDouble o = s.stream().filter(f).mapToInt(m).average(); return o.isPresent() ? o.getAsDouble() : null;
    }
    private Double diff(Double a, Double b) { return (a != null && b != null) ? a - b : null; }

    private Map<String, Object> mergeExtra(List<TaskResult> sessions) {
        Map<String, List<Double>> num = new LinkedHashMap<>();
        Map<String, Object> other = new LinkedHashMap<>();
        sessions.forEach(r -> { if (r.getExtra() == null) return;
            r.getExtra().forEach((k,v) -> { if (v instanceof Number n) num.computeIfAbsent(k, x -> new ArrayList<>()).add(n.doubleValue()); else other.put(k,v); }); });
        Map<String, Object> merged = new LinkedHashMap<>(other);
        num.forEach((k,vals) -> merged.put(k, vals.stream().mapToDouble(Double::doubleValue).average().orElse(0)));
        return merged;
    }

    private String diffStr(double d, String fmt, String suffix) {
        if (d == 0) return "";
        String sign = d > 0 ? "↑" : "↓";
        if (fmt.isBlank()) return String.format(" (%s%d%s)", sign, (int)Math.abs(d), suffix);
        return String.format(" (%s"+fmt+"%s)", sign, Math.abs(d), suffix);
    }

    private record WeekWindow(OffsetDateTime from, OffsetDateTime to,
                               OffsetDateTime prevFrom, OffsetDateTime prevTo, String label) {}
}

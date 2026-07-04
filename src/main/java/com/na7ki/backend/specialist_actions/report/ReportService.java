package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.MonthlyReportEmail;
import com.na7ki.backend.domain.exercise.Entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.CaseInfoData;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.specialist_actions.report.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final EmailService emailService;

    // Scheduled job
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

    // On-demand for authenticated specialist
    public MonthlyReportResponse getReportForSpecialist(Specialist specialist) {
        return buildReport(specialist, monthWindow());
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
        List<TaskResult> results = taskResultRepository
                .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), w.from(), w.to());
        if (results.isEmpty()) return null;

        Map<String, List<TaskResult>> byTask = results.stream()
                .collect(Collectors.groupingBy(TaskResult::getTaskName));

        List<TaskStats> taskStatsList = new ArrayList<>();
        for (Map.Entry<String, List<TaskResult>> e : byTask.entrySet()) {
            taskStatsList.add(buildTaskStats(e.getKey(), e.getValue()));
        }

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
                .tasks(taskStatsList).build();
    }

    private TaskStats buildTaskStats(String name, List<TaskResult> sessions) {
        int n = sessions.size();
        long completed = sessions.stream().filter(TaskResult::isCompleted).count();

        Double avgAcc   = nullableDouble(sessions, r -> r.getAccuracy() != null, r -> r.getAccuracy().doubleValue());
        Double avgAtt   = nullableInt(sessions, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
        Double avgDur   = nullableInt(sessions, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
        Double avgReact = nullableInt(sessions, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);

        Map<String, Integer> errors = new LinkedHashMap<>();
        sessions.forEach(r -> { if (r.getErrorBreakdown() != null)
            r.getErrorBreakdown().forEach((k, v) -> errors.merge(k, v, Integer::sum)); });

        return TaskStats.builder()
                .taskName(name).sessions(n).completedCount(completed)
                .completionPct(completed * 100.0 / n)
                .avgAccuracy(avgAcc).avgAttempts(avgAtt)
                .avgDurationSeconds(avgDur).avgReactionTimeMs(avgReact)
                .topErrors(errors).extraMetrics(mergeExtra(sessions)).build();
    }

    // Email formatter
    private String toEmailBody(MonthlyReportResponse report) {
        StringBuilder sb = new StringBuilder();
        for (PatientMonthlyReport p : report.getPatients()) {
            sb.append("=== Patient: ").append(p.getPatientName()).append(" ===\n");
            if (p.getDiagnosis() != null) sb.append("  Diagnosis: ").append(p.getDiagnosis()).append("\n");
            if (p.getTreatmentStart() != null)
                sb.append("  Treatment: ").append(p.getTreatmentStart()).append(" → ").append(p.getTreatmentEnd()).append("\n");
            sb.append("\n  Task Results:\n");
            for (TaskStats t : p.getTasks()) {
                sb.append("  • ").append(t.getTaskName()).append(":\n");
                sb.append(String.format("      Sessions: %d | Completed: %d/%d (%.0f%%)\n",
                        t.getSessions(), t.getCompletedCount(), t.getSessions(), t.getCompletionPct()));
                if (t.getAvgAccuracy() != null) sb.append(String.format("      Avg accuracy: %.0f%%\n", t.getAvgAccuracy()*100));
                if (t.getAvgAttempts() != null) sb.append(String.format("      Avg attempts: %.1f\n", t.getAvgAttempts()));
                if (t.getAvgDurationSeconds() != null) sb.append(String.format("      Avg duration: %.0fs\n", t.getAvgDurationSeconds()));
                if (t.getAvgReactionTimeMs() != null) sb.append(String.format("      Avg reaction: %.0fms\n", t.getAvgReactionTimeMs()));
                if (!t.getTopErrors().isEmpty()) {
                    sb.append("      Error breakdown:\n");
                    t.getTopErrors().entrySet().stream()
                            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                            .forEach(e -> sb.append("        - ").append(e.getKey()).append(": ").append(e.getValue()).append("\n"));
                }
                if (!t.getExtraMetrics().isEmpty()) {
                    sb.append("      Additional metrics:\n");
                    t.getExtraMetrics().forEach((k,v) -> {
                        if (v instanceof Double d) sb.append(String.format("        - %s: %.2f\n",k,d));
                        else sb.append("        - ").append(k).append(": ").append(v).append("\n");
                    });
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    // Helpers
    private MonthWindow monthWindow() {
        YearMonth last = YearMonth.now().minusMonths(1);
        String label = last.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
        return new MonthWindow(
                last.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC),
                last.atEndOfMonth().atTime(23,59,59).atOffset(ZoneOffset.UTC),
                label);
    }

    private Double nullableDouble(List<TaskResult> s, java.util.function.Predicate<TaskResult> f, java.util.function.ToDoubleFunction<TaskResult> m) {
        OptionalDouble o = s.stream().filter(f).mapToDouble(m).average(); return o.isPresent() ? o.getAsDouble() : null;
    }
    private Double nullableInt(List<TaskResult> s, java.util.function.Predicate<TaskResult> f, java.util.function.ToIntFunction<TaskResult> m) {
        OptionalDouble o = s.stream().filter(f).mapToInt(m).average(); return o.isPresent() ? o.getAsDouble() : null;
    }

    private Map<String, Object> mergeExtra(List<TaskResult> sessions) {
        Map<String, List<Double>> num = new LinkedHashMap<>();
        Map<String, Object> other = new LinkedHashMap<>();
        sessions.forEach(r -> { if (r.getExtra() == null) return;
            r.getExtra().forEach((k,v) -> { if (v instanceof Number n) num.computeIfAbsent(k, x -> new ArrayList<>()).add(n.doubleValue()); else other.put(k,v); }); });
        Map<String, Object> merged = new LinkedHashMap<>(other);
        num.forEach((k,vals) -> merged.put(k, vals.stream().mapToDouble(Double::doubleValue).average().orElse(0)));
        return merged;
    }

    private record MonthWindow(OffsetDateTime from, OffsetDateTime to, String label) {}
}

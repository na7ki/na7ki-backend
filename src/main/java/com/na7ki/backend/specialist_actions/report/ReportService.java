package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.MonthlyReportEmail;
import com.na7ki.backend.domain.exercise.Entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.patient_medical_details.PatientMedicalDetails;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final SpecialistRepository specialistRepository;
    private final TaskResultRepository taskResultRepository;
    private final EmailService emailService;

    /**
     * Builds and sends monthly reports for all specialists.
     * Called by the scheduler on the 1st of each month.
     */
    public void sendMonthlyReports() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        OffsetDateTime from = lastMonth.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime to   = lastMonth.atEndOfMonth().atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        String monthLabel = lastMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));

        List<Specialist> allSpecialists = specialistRepository.findAll();

        for (Specialist specialist : allSpecialists) {
            try {
                String reportBody = buildReportBody(specialist, from, to);

                if (reportBody.isBlank()) {
                    log.info("No patient data for specialist {} in {}, skipping email.", specialist.getEmail(), monthLabel);
                    continue;
                }

                emailService.sendMonthlyReport(
                        specialist.getEmail(),
                        new MonthlyReportEmail(specialist.getName(), monthLabel, reportBody)
                );

                log.info("Monthly report sent to specialist: {}", specialist.getEmail());

            } catch (Exception e) {
                // Log and continue — don't let one failure block other specialists
                log.error("Failed to send monthly report to specialist {}: {}", specialist.getEmail(), e.getMessage());
            }
        }
    }

    private String buildReportBody(Specialist specialist, OffsetDateTime from, OffsetDateTime to) {
        List<Patient> patients = specialist.getPatients();

        if (patients == null || patients.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Patient patient : patients) {
            List<TaskResult> results = taskResultRepository
                    .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), from, to);

            if (results.isEmpty()) continue;

            sb.append("=== Patient: ").append(patient.getName()).append(" ===\n");

            PatientMedicalDetails details = patient.getMedicalDetails();
            if (details != null && details.additionalInfoData() != null
                    && details.additionalInfoData().caseInfoData() != null) {
                sb.append("Diagnosis: ").append(details.additionalInfoData().caseInfoData().primaryDiagnosis()).append("\n");
                sb.append("Treatment period: ")
                        .append(details.additionalInfoData().caseInfoData().startDate())
                        .append(" → ")
                        .append(details.additionalInfoData().caseInfoData().endDate()).append("\n");
            }

            sb.append("\nTask Results for this month:\n");

            // Group by task name
            Map<String, List<TaskResult>> byTask = results.stream()
                    .collect(Collectors.groupingBy(TaskResult::getTaskName));

            for (Map.Entry<String, List<TaskResult>> entry : byTask.entrySet()) {
                String taskName = entry.getKey();
                List<TaskResult> taskSessions = entry.getValue();
                int sessions = taskSessions.size();

                sb.append("  • ").append(taskName).append(":\n");
                sb.append("      Sessions: ").append(sessions).append("\n");

                // Completion rate — always available
                long completedCount = taskSessions.stream().filter(TaskResult::isCompleted).count();
                sb.append(String.format("      Completed: %d/%d (%.0f%%)\n",
                        completedCount, sessions, (completedCount * 100.0 / sessions)));

                // Accuracy
                double avgAccuracy = taskSessions.stream()
                        .filter(r -> r.getAccuracy() != null)
                        .mapToDouble(r -> r.getAccuracy().doubleValue())
                        .average().orElse(-1);
                if (avgAccuracy >= 0) {
                    sb.append(String.format("      Avg accuracy: %.0f%%\n", avgAccuracy * 100));
                }

                // Attempts count
                double avgAttempts = taskSessions.stream()
                        .filter(r -> r.getAttemptsCount() != null)
                        .mapToInt(TaskResult::getAttemptsCount)
                        .average().orElse(-1);
                if (avgAttempts >= 0) {
                    sb.append(String.format("      Avg attempts: %.1f\n", avgAttempts));
                }

                // Duration
                double avgDuration = taskSessions.stream()
                        .filter(r -> r.getDurationSeconds() != null)
                        .mapToInt(TaskResult::getDurationSeconds)
                        .average().orElse(-1);
                if (avgDuration >= 0) {
                    sb.append(String.format("      Avg duration: %.0fs\n", avgDuration));
                }

                // Reaction time
                double avgReaction = taskSessions.stream()
                        .filter(r -> r.getAvgReactionTimeMs() != null)
                        .mapToInt(TaskResult::getAvgReactionTimeMs)
                        .average().orElse(-1);
                if (avgReaction >= 0) {
                    sb.append(String.format("      Avg reaction time: %.0fms\n", avgReaction));
                }

                // Error breakdown — aggregate counts across all sessions
                Map<String, Integer> totalErrors = new HashMap<>();
                for (TaskResult r : taskSessions) {
                    if (r.getErrorBreakdown() != null) {
                        r.getErrorBreakdown().forEach((k, v) ->
                                totalErrors.merge(k, v, Integer::sum));
                    }
                }
                if (!totalErrors.isEmpty()) {
                    sb.append("      Error breakdown:\n");
                    totalErrors.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .forEach(e -> sb.append("        - ").append(e.getKey())
                                    .append(": ").append(e.getValue()).append(" time(s)\n"));
                }

                // Extra — aggregate numeric fields by averaging, show others as-is from last session
                Map<String, Object> mergedExtra = new HashMap<>();
                Map<String, List<Double>> numericAccumulator = new HashMap<>();
                for (TaskResult r : taskSessions) {
                    if (r.getExtra() != null) {
                        r.getExtra().forEach((k, v) -> {
                            if (v instanceof Number n) {
                                numericAccumulator.computeIfAbsent(k, x -> new java.util.ArrayList<>())
                                        .add(n.doubleValue());
                            } else {
                                mergedExtra.put(k, v); // keep last value for non-numeric
                            }
                        });
                    }
                }
                numericAccumulator.forEach((k, vals) ->
                        mergedExtra.put(k, vals.stream().mapToDouble(Double::doubleValue).average().orElse(0)));

                if (!mergedExtra.isEmpty()) {
                    sb.append("      Additional metrics:\n");
                    mergedExtra.forEach((k, v) -> {
                        if (v instanceof Double d) {
                            sb.append(String.format("        - %s: %.2f\n", k, d));
                        } else {
                            sb.append("        - ").append(k).append(": ").append(v).append("\n");
                        }
                    });
                }

                sb.append("\n");
            }

            sb.append("\n");
        }

        return sb.toString().trim();
    }
}

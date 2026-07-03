package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.WeeklyReportEmail;
import com.na7ki.backend.domain.exercise.Entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.task_management.assignment.AssignmentRepository;
import com.na7ki.backend.task_management.assignment.entity.AssignedExercise;
import com.na7ki.backend.task_management.assignment.entity.Assignment;
import com.na7ki.backend.task_management.assignment.entity.enums.ExerciseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    /**
     * Builds and sends weekly reports for all specialists.
     * Called by the scheduler every Monday — covers the previous Mon–Sun week.
     */
    public void sendWeeklyReports() {
        // Last week: Mon to Sun
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY).minusWeeks(1);
        LocalDate weekEnd   = weekStart.plusDays(6); // Sunday

        OffsetDateTime from = weekStart.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime to   = weekEnd.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        // Two weeks ago — for comparison
        OffsetDateTime prevFrom = from.minusWeeks(1);
        OffsetDateTime prevTo   = to.minusWeeks(1);

        String weekLabel = weekStart.format(WEEK_DATE_FMT) + " – " + weekEnd.format(WEEK_YEAR_FMT);

        List<Specialist> allSpecialists = specialistRepository.findAll();

        for (Specialist specialist : allSpecialists) {
            try {
                String reportBody = buildReportBody(specialist, from, to, prevFrom, prevTo);

                if (reportBody.isBlank()) {
                    log.info("No patient data for specialist {} in week {}, skipping email.", specialist.getEmail(), weekLabel);
                    continue;
                }

                emailService.sendWeeklyReport(
                        specialist.getEmail(),
                        new WeeklyReportEmail(specialist.getName(), weekLabel, reportBody)
                );

                log.info("Weekly report sent to specialist: {}", specialist.getEmail());

            } catch (Exception e) {
                log.error("Failed to send weekly report to specialist {}: {}", specialist.getEmail(), e.getMessage());
            }
        }
    }

    private String buildReportBody(Specialist specialist,
                                   OffsetDateTime from, OffsetDateTime to,
                                   OffsetDateTime prevFrom, OffsetDateTime prevTo) {
        List<Patient> patients = specialist.getPatients();
        if (patients == null || patients.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();

        for (Patient patient : patients) {
            List<TaskResult> thisWeek = taskResultRepository
                    .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), from, to);
            List<TaskResult> lastWeek = taskResultRepository
                    .findByPatientIdAndStartedAtBetweenOrderByStartedAt(patient.getUserId(), prevFrom, prevTo);

            // Skip patients with no activity in either week
            if (thisWeek.isEmpty() && lastWeek.isEmpty()) continue;

            sb.append("=== Patient: ").append(patient.getName()).append(" ===\n");

            // ── 1. Activity summary ──────────────────────────────────────────
            int totalSessions = thisWeek.size();
            long activeDays = thisWeek.stream()
                    .map(r -> r.getStartedAt().toLocalDate())
                    .distinct().count();

            // Streak: consecutive days ending on the last active day
            List<LocalDate> sortedDays = thisWeek.stream()
                    .map(r -> r.getStartedAt().toLocalDate())
                    .distinct()
                    .sorted()
                    .toList();
            int streak = 0;
            if (!sortedDays.isEmpty()) {
                streak = 1;
                for (int i = sortedDays.size() - 1; i > 0; i--) {
                    if (sortedDays.get(i).minusDays(1).equals(sortedDays.get(i - 1))) streak++;
                    else break;
                }
            }

            sb.append(String.format("  Activity: %d sessions | %d/7 days active | %d-day streak\n",
                    totalSessions, activeDays, streak));

            if (thisWeek.isEmpty()) {
                sb.append("  ⚠ No sessions recorded this week.\n\n");
                continue;
            }

            // ── 2. Per-task breakdown with week-over-week comparison ─────────
            sb.append("\n  Task breakdown:\n");

            Map<String, List<TaskResult>> thisWeekByTask = thisWeek.stream()
                    .collect(Collectors.groupingBy(TaskResult::getTaskName));
            Map<String, List<TaskResult>> lastWeekByTask = lastWeek.stream()
                    .collect(Collectors.groupingBy(TaskResult::getTaskName));

            for (Map.Entry<String, List<TaskResult>> entry : thisWeekByTask.entrySet()) {
                String taskName = entry.getKey();
                List<TaskResult> sessions = entry.getValue();
                List<TaskResult> prevSessions = lastWeekByTask.getOrDefault(taskName, List.of());

                int sessions_n = sessions.size();
                int prevSessions_n = prevSessions.size();
                String sessionsDiff = prevSessions_n > 0
                        ? formatDiff(sessions_n - prevSessions_n, "", " vs last week") : "";

                long completed = sessions.stream().filter(TaskResult::isCompleted).count();
                double completionPct = completed * 100.0 / sessions_n;

                sb.append("  • ").append(taskName).append(":\n");
                sb.append(String.format("      Sessions: %d%s\n", sessions_n, sessionsDiff));
                sb.append(String.format("      Completed: %d/%d (%.0f%%)\n", completed, sessions_n, completionPct));

                // Accuracy with diff
                double avgAcc = avgDouble(sessions, r -> r.getAccuracy() != null,
                        r -> r.getAccuracy().doubleValue());
                double prevAvgAcc = avgDouble(prevSessions, r -> r.getAccuracy() != null,
                        r -> r.getAccuracy().doubleValue());
                if (avgAcc >= 0) {
                    String diff = prevAvgAcc >= 0
                            ? formatDiff((avgAcc - prevAvgAcc) * 100, "%.0f%%", " vs last week") : "";
                    sb.append(String.format("      Avg accuracy: %.0f%%%s\n", avgAcc * 100, diff));
                }

                // Attempts with diff
                double avgAttempts = avgInt(sessions, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
                double prevAvgAttempts = avgInt(prevSessions, r -> r.getAttemptsCount() != null, TaskResult::getAttemptsCount);
                if (avgAttempts >= 0) {
                    String diff = prevAvgAttempts >= 0
                            ? formatDiff(avgAttempts - prevAvgAttempts, "%.1f", " vs last week") : "";
                    sb.append(String.format("      Avg attempts: %.1f%s\n", avgAttempts, diff));
                }

                // Duration with diff
                double avgDur = avgInt(sessions, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
                double prevAvgDur = avgInt(prevSessions, r -> r.getDurationSeconds() != null, TaskResult::getDurationSeconds);
                if (avgDur >= 0) {
                    String diff = prevAvgDur >= 0
                            ? formatDiff(avgDur - prevAvgDur, "%.0fs", " vs last week") : "";
                    sb.append(String.format("      Avg duration: %.0fs%s\n", avgDur, diff));
                }

                // Reaction time with diff
                double avgReact = avgInt(sessions, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);
                double prevAvgReact = avgInt(prevSessions, r -> r.getAvgReactionTimeMs() != null, TaskResult::getAvgReactionTimeMs);
                if (avgReact >= 0) {
                    String diff = prevAvgReact >= 0
                            ? formatDiff(avgReact - prevAvgReact, "%.0fms", " vs last week") : "";
                    sb.append(String.format("      Avg reaction time: %.0fms%s\n", avgReact, diff));
                }

                // Error breakdown — aggregated this week
                Map<String, Integer> totalErrors = new HashMap<>();
                for (TaskResult r : sessions) {
                    if (r.getErrorBreakdown() != null) {
                        r.getErrorBreakdown().forEach((k, v) -> totalErrors.merge(k, v, Integer::sum));
                    }
                }
                if (!totalErrors.isEmpty()) {
                    sb.append("      Top errors this week:\n");
                    totalErrors.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .limit(3)
                            .forEach(e -> sb.append("        - ").append(e.getKey())
                                    .append(": ").append(e.getValue()).append(" time(s)\n"));
                }

                // Extra — numeric fields averaged
                Map<String, List<Double>> numericAcc = new HashMap<>();
                Map<String, Object> nonNumeric = new HashMap<>();
                for (TaskResult r : sessions) {
                    if (r.getExtra() != null) {
                        r.getExtra().forEach((k, v) -> {
                            if (v instanceof Number n) {
                                numericAcc.computeIfAbsent(k, x -> new ArrayList<>()).add(n.doubleValue());
                            } else {
                                nonNumeric.put(k, v);
                            }
                        });
                    }
                }
                Map<String, Object> extraMerged = new LinkedHashMap<>(nonNumeric);
                numericAcc.forEach((k, vals) ->
                        extraMerged.put(k, vals.stream().mapToDouble(Double::doubleValue).average().orElse(0)));
                if (!extraMerged.isEmpty()) {
                    sb.append("      Additional metrics:\n");
                    extraMerged.forEach((k, v) -> {
                        if (v instanceof Double d) {
                            sb.append(String.format("        - %s: %.2f\n", k, d));
                        } else {
                            sb.append("        - ").append(k).append(": ").append(v).append("\n");
                        }
                    });
                }

                sb.append("\n");
            }

            // ── 3. Best and worst tasks ──────────────────────────────────────
            Map<String, Double> accByTask = new LinkedHashMap<>();
            thisWeekByTask.forEach((task, sessions) -> {
                double acc = avgDouble(sessions, r -> r.getAccuracy() != null,
                        r -> r.getAccuracy().doubleValue());
                if (acc >= 0) accByTask.put(task, acc);
            });

            if (accByTask.size() >= 2) {
                String best  = Collections.max(accByTask.entrySet(), Map.Entry.comparingByValue()).getKey();
                String worst = Collections.min(accByTask.entrySet(), Map.Entry.comparingByValue()).getKey();
                sb.append(String.format("  ★ Best task:  %s (%.0f%% accuracy)\n",
                        best, accByTask.get(best) * 100));
                sb.append(String.format("  ✗ Worst task: %s (%.0f%% accuracy)\n",
                        worst, accByTask.get(worst) * 100));
            }

            // ── 4. Notable errors across all tasks ───────────────────────────
            Map<String, Integer> allErrors = new HashMap<>();
            for (TaskResult r : thisWeek) {
                if (r.getErrorBreakdown() != null) {
                    r.getErrorBreakdown().forEach((k, v) -> allErrors.merge(k, v, Integer::sum));
                }
            }
            if (!allErrors.isEmpty()) {
                sb.append("  Notable errors (all tasks):\n");
                allErrors.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .limit(3)
                        .forEach(e -> sb.append("    - ").append(e.getKey())
                                .append(": ").append(e.getValue()).append(" time(s)\n"));
            }

            // ── 5. Assigned but not attempted ────────────────────────────────
            Set<Integer> attemptedTaskIds = thisWeek.stream()
                    .map(TaskResult::getTaskId)
                    .collect(Collectors.toSet());

            List<Assignment> assignments = assignmentRepository.findByPatient(patient);
            Set<String> skippedTasks = new LinkedHashSet<>();
            for (Assignment assignment : assignments) {
                for (AssignedExercise ex : assignment.getAssignedExercises()) {
                    if (ex.getType() == ExerciseType.TASK && ex.getTask() != null) {
                        Long taskId = ex.getTask().getId();
                        if (!attemptedTaskIds.contains(taskId.intValue())) {
                            skippedTasks.add(ex.getTask().getTitle());
                        }
                    }
                }
            }
            if (!skippedTasks.isEmpty()) {
                sb.append("  ⚠ Assigned but not attempted this week:\n");
                skippedTasks.forEach(t -> sb.append("    - ").append(t).append("\n"));
            }

            sb.append("\n");
        }

        return sb.toString().trim();
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private double avgDouble(List<TaskResult> sessions,
                             java.util.function.Predicate<TaskResult> filter,
                             java.util.function.ToDoubleFunction<TaskResult> mapper) {
        return sessions.stream().filter(filter).mapToDouble(mapper).average().orElse(-1);
    }

    private double avgInt(List<TaskResult> sessions,
                          java.util.function.Predicate<TaskResult> filter,
                          java.util.function.ToIntFunction<TaskResult> mapper) {
        return sessions.stream().filter(filter).mapToInt(mapper).average().orElse(-1);
    }

    /**
     * Returns e.g. " (↑6% vs last week)" or " (↓2 vs last week)" or "" if diff == 0.
     */
    private String formatDiff(double diff, String fmt, String suffix) {
        if (diff == 0) return "";
        String sign = diff > 0 ? "↑" : "↓";
        if (fmt.isBlank()) {
            return String.format(" (%s%d%s)", sign, Math.abs((int) diff), suffix);
        }
        return String.format(" (%s" + fmt + "%s)", sign, Math.abs(diff), suffix);
    }
}

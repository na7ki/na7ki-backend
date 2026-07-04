package com.na7ki.backend.specialist_actions.report;

import com.na7ki.backend.domain.exercise.entity.TaskResult;
import com.na7ki.backend.task_management.assignment.entity.enums.ExerciseType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

// Shared aggregation math for weekly/monthly report building — grouping keys, averages,
// best/worst-of and top-N-errors picking, and period-over-period diffing.
@Component
class ReportStatsSupport {

    Double diff(Double a, Double b) {
        return (a != null && b != null) ? a - b : null;
    }

    Double nullableDouble(List<TaskResult> s, Predicate<TaskResult> f, ToDoubleFunction<TaskResult> m) {
        OptionalDouble o = s.stream().filter(f).mapToDouble(m).average();
        return o.isPresent() ? o.getAsDouble() : null;
    }

    Double nullableInt(List<TaskResult> s, Predicate<TaskResult> f, ToIntFunction<TaskResult> m) {
        OptionalDouble o = s.stream().filter(f).mapToInt(m).average();
        return o.isPresent() ? o.getAsDouble() : null;
    }

    String bestOf(Map<String, Double> accByName) {
        return accByName.isEmpty() ? null : Collections.max(accByName.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    String worstOf(Map<String, Double> accByName) {
        return accByName.size() < 2 ? null : Collections.min(accByName.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    Map<String, Integer> topN(Map<String, Integer> counts, int n) {
        return counts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(n)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    Map<String, Object> mergeExtra(List<TaskResult> sessions) {
        Map<String, List<Double>> num = new LinkedHashMap<>();
        Map<String, Object> other = new LinkedHashMap<>();
        sessions.forEach(r -> { if (r.getExtra() == null) return;
            r.getExtra().forEach((k, v) -> { if (v instanceof Number n) num.computeIfAbsent(k, x -> new ArrayList<>()).add(n.doubleValue()); else other.put(k, v); }); });
        Map<String, Object> merged = new LinkedHashMap<>(other);
        num.forEach((k, vals) -> merged.put(k, vals.stream().mapToDouble(Double::doubleValue).average().orElse(0)));
        return merged;
    }

    // Keyed by (exerciseType, taskId) — grouping by taskName alone risks merging a cognitive
    // task and a practice package that happen to share a display title, and Task ids / Package
    // ids are independent sequences that can collide on the raw id alone.
    record TaskGroupKey(ExerciseType exerciseType, Long taskId) {}
}

package com.na7ki.backend.specialist_actions.report.dto;

import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TaskStats {
    private String taskName;
    private ExerciseType exerciseType;  // TASK = cognitive, QUESTION = non-cognitive practice package
    private int sessions;
    private Integer sessionsDiff;       // vs previous month, null if no previous data
    private long completedCount;
    private double completionPct;

    // nullable — only present when the task sends them
    private Double avgAccuracy;
    private Double accuracyDiff;        // vs previous month, null if no previous data
    private Double avgAttempts;
    private Double attemptsDiff;
    private Double avgDurationSeconds;
    private Double durationDiff;
    private Double avgReactionTimeMs;
    private Double reactionDiff;

    private Map<String, Integer> topErrors;
    private Map<String, Object> extraMetrics;
}

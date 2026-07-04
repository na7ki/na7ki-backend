package com.na7ki.backend.specialist_actions.report.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TaskWeeklyStats {
    private String taskName;
    private int sessions;
    private Integer sessionsDiff;       // vs previous week, null if no previous data
    private long completedCount;
    private double completionPct;

    private Double avgAccuracy;
    private Double accuracyDiff;        // vs previous week, null if no previous data
    private Double avgAttempts;
    private Double attemptsDiff;
    private Double avgDurationSeconds;
    private Double durationDiff;
    private Double avgReactionTimeMs;
    private Double reactionDiff;

    private Map<String, Integer> topErrors;
    private Map<String, Object> extraMetrics;
}

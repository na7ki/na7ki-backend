package com.na7ki.backend.specialist_actions.report.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TaskStats {
    private String taskName;
    private int sessions;
    private long completedCount;
    private double completionPct;

    // nullable — only present when the task sends them
    private Double avgAccuracy;
    private Double avgAttempts;
    private Double avgDurationSeconds;
    private Double avgReactionTimeMs;

    private Map<String, Integer> topErrors;
    private Map<String, Object> extraMetrics;
}

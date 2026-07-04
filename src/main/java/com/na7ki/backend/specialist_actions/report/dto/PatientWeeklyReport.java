package com.na7ki.backend.specialist_actions.report.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PatientWeeklyReport {
    private String patientName;
    private String patientSpecificId;
    private String diagnosis;
    private LocalDate treatmentStart;

    private int totalSessions;
    private long activeDays;
    private int streak;

    private List<TaskWeeklyStats> tasks;

    private String bestTask;            // task with highest avg accuracy, null if no accuracy data
    private String worstTask;
    private Map<String, Integer> notableErrors;
    private List<String> skippedTasks;
}

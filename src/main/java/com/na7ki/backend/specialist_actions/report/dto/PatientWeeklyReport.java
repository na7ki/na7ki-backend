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
    private int possibleActiveDays;   // days in this report window on/after the patient's registration date, capped at 7
    private int streak;

    private List<TaskWeeklyStats> tasks;

    // Best/worst and notable errors are split per category since comparing accuracy or error
    // taxonomies across a cognitive task and a non-cognitive practice package isn't meaningful.
    private String bestCognitiveTask;       // highest avg accuracy among TASK-type entries, null if none
    private String worstCognitiveTask;
    private Map<String, Integer> notableCognitiveErrors;

    private String bestPracticePackage;     // highest avg accuracy among QUESTION-type entries, null if none
    private String worstPracticePackage;
    private Map<String, Integer> notablePracticeErrors;

    private List<String> skippedTasks;

    // Empty = on track. Non-empty = specialist should look closer; drives the weekly email's
    // "needs attention" digest so a large caseload doesn't turn into a full dump of every patient.
    private List<String> attentionReasons;
}

package com.na7ki.backend.domain.exercise.dto;

import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Inbound payload for POST /api/patients/{patientId}/task-results.
 * patientId is intentionally NOT a field here — it comes from the URL path.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResultRequest {

    private Long taskId;
    // TASK = cognitive task (taskId references tasks.id); QUESTION = non-cognitive package (taskId references packages.id)
    private ExerciseType exerciseType;
    private String taskName;
    private OffsetDateTime startedAt;
    private OffsetDateTime completedAt;
    private Boolean completed;
    private Integer durationSeconds;
    private Integer totalRounds;
    private Integer correctRounds;
    private Double accuracy;
    private Integer attemptsCount;
    private Integer avgReactionTimeMs;
    private Map<String, Integer> errorBreakdown;
    private Map<String, Object> extra;
}

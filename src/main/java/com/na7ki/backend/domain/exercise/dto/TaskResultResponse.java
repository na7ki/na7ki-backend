package com.na7ki.backend.domain.exercise.dto;

import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Outbound shape for GET /api/patients/{patientId}/task-results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResultResponse {

    private Long id;
    private Long patientId;
    private Long taskId;
    private ExerciseType exerciseType;
    private String taskName;
    private OffsetDateTime startedAt;
    private OffsetDateTime completedAt;
    private boolean completed;
    private Integer durationSeconds;
    private Integer totalRounds;
    private Integer correctRounds;
    private Double accuracy;
    private Integer attemptsCount;
    private Integer avgReactionTimeMs;
    private Map<String, Integer> errorBreakdown;
    private Map<String, Object> extra;
    private OffsetDateTime createdAt;
}

package com.na7ki.backend.domain.exercise.dto;

import com.na7ki.backend.domain.exercise.entity.TaskResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaskResultMapper {

    public TaskResult toEntity(Long patientId, TaskResultRequest req) {
        TaskResult entity = new TaskResult();
        entity.setPatientId(patientId);
        entity.setTaskId(req.getTaskId());
        entity.setExerciseType(req.getExerciseType());
        entity.setTaskName(req.getTaskName());
        entity.setStartedAt(req.getStartedAt());
        entity.setCompletedAt(req.getCompletedAt());
        entity.setCompleted(Boolean.TRUE.equals(req.getCompleted()));
        entity.setDurationSeconds(req.getDurationSeconds());
        entity.setTotalRounds(req.getTotalRounds());
        entity.setCorrectRounds(req.getCorrectRounds());
        entity.setAccuracy(req.getAccuracy() != null ? BigDecimal.valueOf(req.getAccuracy()) : null);
        entity.setAttemptsCount(req.getAttemptsCount());
        entity.setAvgReactionTimeMs(req.getAvgReactionTimeMs());
        entity.setErrorBreakdown(req.getErrorBreakdown());
        entity.setExtra(req.getExtra());
        return entity;
    }

    public TaskResultResponse toResponse(TaskResult entity) {
        return TaskResultResponse.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .taskId(entity.getTaskId())
                .exerciseType(entity.getExerciseType())
                .taskName(entity.getTaskName())
                .startedAt(entity.getStartedAt())
                .completedAt(entity.getCompletedAt())
                .completed(entity.isCompleted())
                .durationSeconds(entity.getDurationSeconds())
                .totalRounds(entity.getTotalRounds())
                .correctRounds(entity.getCorrectRounds())
                .accuracy(entity.getAccuracy() != null ? entity.getAccuracy().doubleValue() : null)
                .attemptsCount(entity.getAttemptsCount())
                .avgReactionTimeMs(entity.getAvgReactionTimeMs())
                .errorBreakdown(entity.getErrorBreakdown())
                .extra(entity.getExtra())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

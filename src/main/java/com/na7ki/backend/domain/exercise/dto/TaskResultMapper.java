package com.na7ki.backend.domain.exercise.dto;

import com.na7ki.backend.domain.exercise.Entity.Cases;
import com.na7ki.backend.domain.exercise.Entity.TaskResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaskResultMapper {

    public TaskResult toEntity(Long caseId, TaskResultRequest req, Cases caseEntity) {
        TaskResult entity = new TaskResult();
        entity.setCaseEntity(caseEntity);
        entity.setTaskId(req.getTaskId());
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
                .caseId(entity.getCaseEntity() != null ? entity.getCaseEntity().getId() : null)
                .taskId(entity.getTaskId())
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

package com.na7ki.backend.domain.exercise.validation;

import com.na7ki.backend.domain.exercise.Repository.CasesRepository;
import com.na7ki.backend.domain.exercise.dto.TaskResultRequest;
import com.na7ki.backend.domain.exercise.exception.TaskResultValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TaskResultValidator {

    private static final Set<Integer> VALID_TASK_IDS = Set.of(101, 102, 103, 104, 105, 106, 107, 108, 109);

    private final CasesRepository casesRepository;

    public TaskResultValidator(CasesRepository casesRepository) {
        this.casesRepository = casesRepository;
    }

    public void validate(Long caseId, TaskResultRequest req) {
        List<String> errors = new ArrayList<>();

        if (!casesRepository.existsById(caseId)) {
            errors.add("Case not found: " + caseId);
        }

        if (req.getTaskId() == null) {
            errors.add("taskId is required");
        } else if (!VALID_TASK_IDS.contains(req.getTaskId())) {
            errors.add("taskId must be one of " + VALID_TASK_IDS + ", got: " + req.getTaskId());
        }

        if (req.getTaskName() == null || req.getTaskName().isBlank()) {
            errors.add("taskName is required");
        }

        if (req.getStartedAt() == null) {
            errors.add("startedAt is required");
        }

        if (req.getCompletedAt() == null) {
            errors.add("completedAt is required");
        }

        if (req.getStartedAt() != null && req.getCompletedAt() != null
                && !req.getStartedAt().isBefore(req.getCompletedAt())) {
            errors.add("startedAt must be before completedAt");
        }

        if (req.getDurationSeconds() == null) {
            errors.add("durationSeconds is required");
        } else if (req.getDurationSeconds() < 0) {
            errors.add("durationSeconds must be >= 0");
        }

        if (req.getTotalRounds() == null) {
            errors.add("totalRounds is required");
        } else if (req.getTotalRounds() < 0) {
            errors.add("totalRounds must be >= 0");
        }

        if (req.getCorrectRounds() == null) {
            errors.add("correctRounds is required");
        } else if (req.getCorrectRounds() < 0) {
            errors.add("correctRounds must be >= 0");
        }

        if (req.getTotalRounds() != null && req.getCorrectRounds() != null
                && req.getCorrectRounds() > req.getTotalRounds()) {
            errors.add("correctRounds cannot exceed totalRounds");
        }

        if (req.getAttemptsCount() == null) {
            errors.add("attemptsCount is required");
        } else if (req.getAttemptsCount() < 0) {
            errors.add("attemptsCount must be >= 0");
        }

        if (req.getCompleted() == null) {
            errors.add("completed is required");
        }

        if (req.getErrorBreakdown() == null) {
            errors.add("errorBreakdown is required");
        }

        if (req.getExtra() == null) {
            errors.add("extra is required");
        }

        if (!errors.isEmpty()) {
            throw new TaskResultValidationException(errors);
        }
    }
}

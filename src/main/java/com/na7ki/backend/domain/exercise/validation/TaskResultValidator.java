package com.na7ki.backend.domain.exercise.validation;

import com.na7ki.backend.domain.exercise.Repository.PackagesRepository;
import com.na7ki.backend.domain.exercise.Repository.TaskRepository;
import com.na7ki.backend.domain.user.repository.PatientRepository;
import com.na7ki.backend.domain.exercise.dto.TaskResultRequest;
import com.na7ki.backend.domain.exercise.exception.TaskResultValidationException;
import com.na7ki.backend.task_management.assignment.entity.enums.ExerciseType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskResultValidator {

    private final PatientRepository patientRepository;
    private final TaskRepository taskRepository;
    private final PackagesRepository packagesRepository;

    public TaskResultValidator(PatientRepository patientRepository,
                                TaskRepository taskRepository,
                                PackagesRepository packagesRepository) {
        this.patientRepository = patientRepository;
        this.taskRepository = taskRepository;
        this.packagesRepository = packagesRepository;
    }

    public void validate(Long patientId, TaskResultRequest req) {
        List<String> errors = new ArrayList<>();

        if (!patientRepository.existsById(patientId)) {
            errors.add("Patient not found: " + patientId);
        }

        if (req.getExerciseType() == null) {
            errors.add("exerciseType is required");
        }

        if (req.getTaskId() == null) {
            errors.add("taskId is required");
        } else if (req.getExerciseType() == ExerciseType.TASK && !taskRepository.existsById(req.getTaskId())) {
            errors.add("No cognitive task found with id: " + req.getTaskId());
        } else if (req.getExerciseType() == ExerciseType.QUESTION && !packagesRepository.existsById(req.getTaskId())) {
            errors.add("No package found with id: " + req.getTaskId());
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

        if (req.getDurationSeconds() != null && req.getDurationSeconds() < 0) {
            errors.add("durationSeconds must be >= 0");
        }

        if (req.getTotalRounds() != null && req.getTotalRounds() < 0) {
            errors.add("totalRounds must be >= 0");
        }

        if (req.getCorrectRounds() != null && req.getCorrectRounds() < 0) {
            errors.add("correctRounds must be >= 0");
        }

        if (req.getTotalRounds() != null && req.getCorrectRounds() != null
                && req.getCorrectRounds() > req.getTotalRounds()) {
            errors.add("correctRounds cannot exceed totalRounds");
        }

        if (req.getAttemptsCount() != null && req.getAttemptsCount() < 0) {
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

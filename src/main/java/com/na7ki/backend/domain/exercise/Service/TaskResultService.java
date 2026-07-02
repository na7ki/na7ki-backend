package com.na7ki.backend.domain.exercise.Service;

import com.na7ki.backend.domain.exercise.Entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.exercise.dto.TaskResultMapper;
import com.na7ki.backend.domain.exercise.dto.TaskResultRequest;
import com.na7ki.backend.domain.exercise.dto.TaskResultResponse;
import com.na7ki.backend.domain.exercise.exception.TaskResultValidationException;
import com.na7ki.backend.domain.exercise.validation.TaskResultValidator;
import com.na7ki.backend.domain.user.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskResultService {

    private final TaskResultRepository taskResultRepository;
    private final PatientRepository patientRepository;
    private final TaskResultValidator validator;
    private final TaskResultMapper mapper;

    public TaskResultService(
            TaskResultRepository taskResultRepository,
            PatientRepository patientRepository,
            TaskResultValidator validator,
            TaskResultMapper mapper
    ) {
        this.taskResultRepository = taskResultRepository;
        this.patientRepository = patientRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Transactional
    public TaskResultResponse submit(Long patientId, TaskResultRequest req) {
        validator.validate(patientId, req);

        // Idempotency: resubmitting the same (patientId, taskId, startedAt) returns the existing row.
        var existing = taskResultRepository.findByPatientIdAndTaskIdAndStartedAt(
                patientId, req.getTaskId(), req.getStartedAt());
        if (existing.isPresent()) {
            return mapper.toResponse(existing.get());
        }

        TaskResult entity = mapper.toEntity(patientId, req);
        TaskResult saved = taskResultRepository.save(entity);
        return mapper.toResponse(saved);
    }

    public List<TaskResultResponse> getForPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new TaskResultValidationException(List.of("Patient not found: " + patientId));
        }
        return taskResultRepository.findByPatientIdOrderByStartedAtDesc(patientId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

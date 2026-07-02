package com.na7ki.backend.domain.exercise.Service;

import com.na7ki.backend.domain.exercise.Entity.Cases;
import com.na7ki.backend.domain.exercise.Entity.TaskResult;
import com.na7ki.backend.domain.exercise.Repository.CasesRepository;
import com.na7ki.backend.domain.exercise.Repository.TaskResultRepository;
import com.na7ki.backend.domain.exercise.dto.TaskResultMapper;
import com.na7ki.backend.domain.exercise.dto.TaskResultRequest;
import com.na7ki.backend.domain.exercise.dto.TaskResultResponse;
import com.na7ki.backend.domain.exercise.exception.TaskResultValidationException;
import com.na7ki.backend.domain.exercise.validation.TaskResultValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskResultService {

    private final TaskResultRepository taskResultRepository;
    private final CasesRepository casesRepository;
    private final TaskResultValidator validator;
    private final TaskResultMapper mapper;

    public TaskResultService(
            TaskResultRepository taskResultRepository,
            CasesRepository casesRepository,
            TaskResultValidator validator,
            TaskResultMapper mapper
    ) {
        this.taskResultRepository = taskResultRepository;
        this.casesRepository = casesRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Transactional
    public TaskResultResponse submit(Long caseId, TaskResultRequest req) {
        // Run all validation rules first — collects every failure into one 422.
        validator.validate(caseId, req);

        // Idempotency: the app may resubmit the exact same (caseId, taskId, startedAt)
        // on reconnect. Treat a duplicate as a no-op success rather than letting
        // the DB unique constraint throw a 500.
        var existing = taskResultRepository.findByCaseEntity_IdAndTaskIdAndStartedAt(
                caseId, req.getTaskId(), req.getStartedAt());
        if (existing.isPresent()) {
            return mapper.toResponse(existing.get());
        }

        // caseId existence was already confirmed by the validator, so this is safe.
        Cases caseEntity = casesRepository.findById(caseId)
                .orElseThrow(() -> new TaskResultValidationException(
                        List.of("Case not found: " + caseId)));

        TaskResult entity = mapper.toEntity(caseId, req, caseEntity);
        TaskResult saved = taskResultRepository.save(entity);
        return mapper.toResponse(saved);
    }

    public List<TaskResultResponse> getForCase(Long caseId) {
        if (!casesRepository.existsById(caseId)) {
            throw new TaskResultValidationException(List.of("Case not found: " + caseId));
        }
        return taskResultRepository.findByCaseEntity_IdOrderByStartedAtDesc(caseId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

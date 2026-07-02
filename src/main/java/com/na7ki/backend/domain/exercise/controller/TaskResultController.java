package com.na7ki.backend.domain.exercise.controller;

import com.na7ki.backend.domain.exercise.Service.TaskResultService;
import com.na7ki.backend.domain.exercise.dto.TaskResultRequest;
import com.na7ki.backend.domain.exercise.dto.TaskResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * POST /api/patients/{patientId}/task-results  — submit a task result
 * GET  /api/patients/{patientId}/task-results  — fetch all results for a patient
 *
 * Auth (Bearer token) is enforced upstream via Spring Security filter chain.
 */
@RestController
@RequestMapping("/api/patients/{patientId}/task-results")
public class TaskResultController {

    private final TaskResultService taskResultService;

    public TaskResultController(TaskResultService taskResultService) {
        this.taskResultService = taskResultService;
    }

    @PostMapping
    public ResponseEntity<TaskResultResponse> submit(
            @PathVariable Long patientId,
            @RequestBody TaskResultRequest request
    ) {
        TaskResultResponse response = taskResultService.submit(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResultResponse>> getForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(taskResultService.getForPatient(patientId));
    }
}

package com.na7ki.backend.domain.exercise.controller;

import com.na7ki.backend.domain.exercise.Service.TaskResultService;
import com.na7ki.backend.domain.exercise.dto.TaskResultRequest;
import com.na7ki.backend.domain.exercise.dto.TaskResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * POST /api/cases/{caseId}/assignmentTask-results  — submit a session result
 * GET  /api/cases/{caseId}/assignmentTask-results  — fetch all results for a case
 *
 * Auth (Bearer token) is enforced upstream via Spring Security filter chain.
 */
@RestController
@RequestMapping("/api/cases/{caseId}/assignmentTask-results")
public class TaskResultController {

    private final TaskResultService taskResultService;

    public TaskResultController(TaskResultService taskResultService) {
        this.taskResultService = taskResultService;
    }

    @PostMapping
    public ResponseEntity<TaskResultResponse> submit(
            @PathVariable Long caseId,
            @RequestBody TaskResultRequest request
    ) {
        TaskResultResponse response = taskResultService.submit(caseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResultResponse>> getForCase(@PathVariable Long caseId) {
        return ResponseEntity.ok(taskResultService.getForCase(caseId));
    }
}

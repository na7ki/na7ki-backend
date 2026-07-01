package com.na7ki.backend.task_management;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.task_management.dto.request.AssignTaskRequest;
import com.na7ki.backend.task_management.dto.response.exercises_list_response.AssignmentPackage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskManagementController {

    private final TaskManagementService taskManagementService;





    @GetMapping("/specialist")
    public ResponseEntity<List<AssignmentPackage>> getAllExercises () {
        return ResponseEntity.status(HttpStatus.OK).body(taskManagementService.getAllExercises());
    }

    @PostMapping("/specialist/{patientSpecificId}")
    public ResponseEntity<Void> assignTask (
            @PathVariable
            @Pattern(regexp = "^PT\\d+$", message = "Invalid Patient ID format. Must be PT followed by one or more digits")
            String patientSpecificId,

            @AuthenticationPrincipal User specialist,

            @RequestBody @Valid AssignTaskRequest request
    ) {
        taskManagementService.assignTask(((Specialist) specialist), patientSpecificId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/patient")
    public ResponseEntity<List<AssignmentPackage>> getExercisesOfPatient (@AuthenticationPrincipal User patient) {
        return ResponseEntity.status(HttpStatus.OK).body(taskManagementService.getExercisesOfPatient(((Patient) patient)));
    }

}

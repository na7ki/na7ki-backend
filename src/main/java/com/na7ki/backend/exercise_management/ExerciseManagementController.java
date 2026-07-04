package com.na7ki.backend.exercise_management;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.exercise_management.dto.request.AssignExerciseRequest;
import com.na7ki.backend.exercise_management.dto.response.exercises_list_response.AssignmentPackage;
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
public class ExerciseManagementController {

    private final ExerciseManagementService exerciseManagementService;





    @GetMapping("/specialist")
    public ResponseEntity<List<AssignmentPackage>> getAllExercises () {
        return ResponseEntity.status(HttpStatus.OK).body(exerciseManagementService.getAllExercises());
    }

    @PostMapping("/specialist/{patientSpecificId}")
    public ResponseEntity<Void> assignExercise(
            @PathVariable
            @Pattern(regexp = "^PT\\d+$", message = "Invalid Patient ID format. Must be PT followed by one or more digits")
            String patientSpecificId,

            @AuthenticationPrincipal User specialist,

            @RequestBody @Valid AssignExerciseRequest request
    ) {
        exerciseManagementService.assignTask(((Specialist) specialist), patientSpecificId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/patient")
    public ResponseEntity<List<AssignmentPackage>> getExercisesOfPatient (@AuthenticationPrincipal User patient) {
        return ResponseEntity.status(HttpStatus.OK).body(exerciseManagementService.getExercisesOfPatient(((Patient) patient)));
    }

}

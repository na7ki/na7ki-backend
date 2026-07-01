package com.na7ki.backend.specialist_actions.manage_tasks;

import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.request.AssignTaskRequest;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_response.Package;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialist/tasks")
@RequiredArgsConstructor
public class ManageTasksController {

    private final ManageTasksService manageTasksService;





    @GetMapping
    public ResponseEntity<List<Package>> getAllExercises () {
        return ResponseEntity.status(HttpStatus.OK).body(manageTasksService.getAllExercises());
    }

    @PostMapping("/{patientSpecificId}")
    public ResponseEntity<Void> assignTask (
            @PathVariable
            @Pattern(regexp = "^PT\\d+$", message = "Invalid Patient ID format. Must be PT followed by one or more digits")
            String patientSpecificId,

            @AuthenticationPrincipal User specialist,

            @RequestBody @Valid AssignTaskRequest request
    ) {
        manageTasksService.assignTask(((Specialist) specialist), patientSpecificId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

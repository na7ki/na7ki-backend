package com.na7ki.backend.specialist_actions.manage_tasks;

import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.Package;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

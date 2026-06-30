package com.na7ki.backend.specialist_actions.manage_tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialist/tasks")
@RequiredArgsConstructor
public class ManageTasksController {

    private final ManageTasksService manageTasksService;






}

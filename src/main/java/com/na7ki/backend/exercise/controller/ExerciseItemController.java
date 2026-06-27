package com.na7ki.backend.exercise.controller;

import com.na7ki.backend.exercise.DTO.ExerciseItemResponse;
import com.na7ki.backend.exercise.Service.ExerciseItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercises/items")
public class ExerciseItemController {

    private final ExerciseItemService exerciseItemService;

    public ExerciseItemController(ExerciseItemService exerciseItemService) {
        this.exerciseItemService = exerciseItemService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseItemResponse>> getAllItems() {
        return ResponseEntity.ok(exerciseItemService.getAllItems());
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<List<ExerciseItemResponse>> getItemsByTaskIdentifier(@PathVariable String identifier) {
        if (identifier.matches("\\d+")) {
            return ResponseEntity.ok(exerciseItemService.getItemsByTaskId(Long.valueOf(identifier)));
        }

        return ResponseEntity.ok(exerciseItemService.getItemsByTaskKey(identifier));
    }
}
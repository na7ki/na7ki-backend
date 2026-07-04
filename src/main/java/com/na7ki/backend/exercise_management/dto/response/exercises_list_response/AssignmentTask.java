package com.na7ki.backend.exercise_management.dto.response.exercises_list_response;

public record AssignmentTask(

        Long id,
        String taskKey,
        String category,
        String title,
        String description

) {
}

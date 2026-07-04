package com.na7ki.backend.exercise_management.dto.response.patient_assigned_exercises_list_response;

import lombok.Data;

@Data
public class AssignedTask extends AssignedExerciseDto {

    private Long id;
    private String taskKey;
    private String category;
    private String title;
    private String description;

}

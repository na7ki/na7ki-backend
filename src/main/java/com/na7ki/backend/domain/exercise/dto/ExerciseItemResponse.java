package com.na7ki.backend.domain.exercise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseItemResponse {
    private Long id;
    private String taskKey; // The identifier Flutter uses
    private String key;     // Maps to itemKey in the database
    private String label;
    private String imageUrl;
    private String soundUrl;
}   
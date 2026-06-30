package com.na7ki.backend.exercise.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String taskKey;
    private String category;
    private Integer orderIndex;
    private String imageUrl; // Extracted from the related Image entity
}
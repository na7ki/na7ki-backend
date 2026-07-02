package com.na7ki.backend.domain.exercise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CaseRequest {

    @NotBlank(message = "childName is required")
    private String childName;
}

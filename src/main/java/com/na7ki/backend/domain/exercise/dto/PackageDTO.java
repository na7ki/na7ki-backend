package com.na7ki.backend.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageDTO {
    private Long id;
    private String title;
    private String description;
    private ImageDTO coverImage;
}

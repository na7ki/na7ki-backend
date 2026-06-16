package com.na7ki.backend.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDTO {
    private Long id;
    private ImageDTO image;
    private String type;  // "SINGULAR" or "PLURAL"
}

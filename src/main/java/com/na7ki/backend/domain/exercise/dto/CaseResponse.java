package com.na7ki.backend.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseResponse {

    private Long id;
    private Long userId;
    private String childName;
}

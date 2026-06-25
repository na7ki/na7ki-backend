package com.na7ki.backend.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionResultDTO {
    private Long sessionId;
    private Integer score;
    private Integer totalQuestions;
    private Double percentage;
    private List<QuestionResultDTO> results;
}

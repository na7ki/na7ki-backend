package com.na7ki.backend.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionDetailDTO {
    private Long sessionId;
    private Long userId;
    private Long packageId;
    private String packageTitle;
    private int score;
    private int totalQuestions;
    private double percentage;
    private LocalDateTime createdAt;
    private List<QuestionResultDTO> results;
}

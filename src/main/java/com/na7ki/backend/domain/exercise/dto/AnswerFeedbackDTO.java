package com.na7ki.backend.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFeedbackDTO {
    private Long questionId;
    private Long choiceId;
    private String questionText;
    private String correctAnswer;
    private String userAnswer;
    private boolean isCorrect;
    private String feedbackType; // "correct" or "incorrect"
}

package com.na7ki.backend.domain.exercise.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

// ── Response: returned after session is submitted ────────────────────────────
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResultResponse {
    private Long sessionId;
    private int score;                      // number of correct answers
    private int totalQuestions;             // total questions in session
    private double percentage;             // e.g. 80.0
    private List<Long> wrongQuestionIds;   // IDs of wrong questions for further exercises
    private List<QuestionResult> details;  // per-question breakdown

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionResult {
        private Long questionId;
        private String questionText;
        private String correctAnswer;
        private String selectedAnswer;
        private boolean isCorrect;
    }
}

package com.na7ki.backend.domain.exercise.dto;

import lombok.Data;
import java.util.List;

// ── Request: user submits all answers for a session ──────────────────────────
@Data
public class SubmitSessionRequest {
    private Long userId;
    private Long packageId;
    private List<AnswerItem> answers;

    @Data
    public static class AnswerItem {
        private Long questionId;
        private String selectedType;   // "SINGULAR" or "PLURAL"
    }
}

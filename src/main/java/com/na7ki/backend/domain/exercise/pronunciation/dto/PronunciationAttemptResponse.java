package com.na7ki.backend.domain.exercise.pronunciation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Outbound shape for the pronunciation-attempts endpoints, returned to Flutter.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PronunciationAttemptResponse {

    private Long id;
    private Long patientId;
    private Integer wordId;
    private Double probability;
    private boolean correct;
    private Double thresholdUsed;
    private String modelVersion;
    private OffsetDateTime createdAt;
}

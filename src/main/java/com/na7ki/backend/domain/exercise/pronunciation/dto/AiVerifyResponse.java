package com.na7ki.backend.domain.exercise.pronunciation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Mirrors na7ki-ai's app.schemas.VerifyResponse. Field names on the wire are
 * snake_case (plain pydantic serialization, no alias generator), so they're
 * mapped explicitly rather than relying on a naming strategy.
 */
@Data
public class AiVerifyResponse {

    @JsonProperty("word_id")
    private Integer wordId;

    private Double probability;

    @JsonProperty("is_correct")
    private Boolean isCorrect;

    @JsonProperty("threshold_used")
    private Double thresholdUsed;

    @JsonProperty("model_version")
    private String modelVersion;
}

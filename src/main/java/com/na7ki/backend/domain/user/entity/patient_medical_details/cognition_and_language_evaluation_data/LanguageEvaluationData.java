package com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record LanguageEvaluationData(

        @NotNull(message = "expressiveness score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short expressivenessScore,

        @NotNull(message = "receptiveness score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short receptivenessScore,

        @NotNull(message = "internal language score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short internalLanguageScore

) {
}

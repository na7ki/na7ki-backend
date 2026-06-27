package com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record LanguageAwarenessData(

        @NotNull(message = "body parts identification score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short bodyPartsIdentificationScore,

        @NotNull(message = "fruits identification score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short fruitsIdentificationScore,

        @NotNull(message = "objects identification score is required")
        @Min(0)
        @Max(60)
        @Digits(integer= 2, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short objectsIdentificationScore

) {
}

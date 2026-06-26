package com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Embeddable
public record LanguageEvaluationData(

        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short expressiveness,

        @Min(0)
        @Max(50)
        @Digits(integer= 2, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short Receptiveness,

        @Min(0)
        @Max(75)
        @Digits(integer= 2, fraction = 0)
        @Column(nullable = false, updatable = false)
        Short internalLanguageScore

) {
}

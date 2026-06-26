package com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record LanguageAwarenessData(

        @Column (nullable = false)
        Short bodyPartsIdentificationScore,

        @Column (nullable = false)
        Short fruitsIdentificationScore,

        @Column (nullable = false)
        Short objectsIdentificationScore

) {
}

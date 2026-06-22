package com.na7ki.backend.domain.user.entity.patient_details.language_and_cognition_evaluation_data;

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

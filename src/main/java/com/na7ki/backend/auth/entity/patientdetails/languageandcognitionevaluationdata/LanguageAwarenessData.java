package com.na7ki.backend.auth.entity.patientdetails.languageandcognitionevaluationdata;

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

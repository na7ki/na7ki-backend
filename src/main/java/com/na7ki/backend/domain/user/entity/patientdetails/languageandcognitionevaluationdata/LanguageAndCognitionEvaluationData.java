package com.na7ki.backend.domain.user.entity.patientdetails.languageandcognitionevaluationdata;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record LanguageAndCognitionEvaluationData(

        @Embedded
        LanguageAwarenessData data1,

        @Embedded
        LanguageEvaluationData data2

) {
}

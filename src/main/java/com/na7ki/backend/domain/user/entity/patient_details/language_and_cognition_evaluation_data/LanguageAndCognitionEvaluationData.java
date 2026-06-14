package com.na7ki.backend.domain.user.entity.patient_details.language_and_cognition_evaluation_data;

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

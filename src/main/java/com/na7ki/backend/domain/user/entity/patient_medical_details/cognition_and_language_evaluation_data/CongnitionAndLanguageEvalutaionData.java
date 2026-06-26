package com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record CongnitionAndLanguageEvalutaionData(

        @Embedded
        LanguageEvaluationData languageEvaluationData,

        @Embedded
        LanguageAwarenessData languageAwarenessData

) {
}

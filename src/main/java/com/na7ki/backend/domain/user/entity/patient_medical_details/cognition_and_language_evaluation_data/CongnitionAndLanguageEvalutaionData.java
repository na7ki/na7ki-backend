package com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record CongnitionAndLanguageEvalutaionData(

        @Embedded @NotNull(message = "language evaluation data is required") @Valid LanguageEvaluationData languageEvaluationData,

        @Embedded @NotNull(message = "language awareness data is required") @Valid  LanguageAwarenessData languageAwarenessData

) {
}

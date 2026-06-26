package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.congnition_and_language_evalutaion_data_dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record LanguageEvaluationDataDto(

        @NotNull(message = "expressiveness score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        Short expressivenessScore,

        @NotNull(message = "receptiveness score is required")
        @Min(0)
        @Max(50)
        @Digits(integer= 2, fraction = 0)
        Short receptivenessScore,

        @NotNull(message = "internal language score is required")
        @Min(0)
        @Max(75)
        @Digits(integer= 2, fraction = 0)
        Short internalLanguageScore

) {
}

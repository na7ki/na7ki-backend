package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.congnition_and_language_evalutaion_data_dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record LanguageAwarenessDataDto(

        @NotNull(message = "body parts identification score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        Short bodyPartsIdentificationScore,

        @NotNull(message = "fruits identification score is required")
        @Min(0)
        @Max(100)
        @Digits(integer= 3, fraction = 0)
        Short fruitsIdentificationScore,

        @NotNull(message = "objects identification score is required")
        @Min(0)
        @Max(60)
        @Digits(integer= 2, fraction = 0)
        Short objectsIdentificationScore

) {
}

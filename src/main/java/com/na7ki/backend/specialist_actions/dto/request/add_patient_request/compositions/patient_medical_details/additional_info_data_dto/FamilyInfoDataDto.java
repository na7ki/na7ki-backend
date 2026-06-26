package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.additional_info_data_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record FamilyInfoDataDto(

        @NotNull(message = "number of siblings is required")
        @PositiveOrZero
        Short noSiblings,

        @NotNull(message = "order in siblings is required")
        @PositiveOrZero
        Short orderInSiblings

) {
}

package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.additional_info_data_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CaseInfoDataDto(

        @NotNull(message = "date of beginning of treatment is required")
        @JsonFormat(pattern = "yyyy-M-d")
        LocalDate startDate,

        @NotNull(message = "date of end of treatment is required")
        @JsonFormat(pattern = "yyyy-M-d")
        LocalDate endDate,

        @NotBlank(message = "primary diagnosis is required")
        @Size(max = 300, message = "can't accept more than 300 characters")
        String primaryDiagnosis,

        @Size(max = 1000, message = "can't accept more than 1000 characters")
        String notes,

        @NotNull(message = "required data")
        Boolean hasPreviousTreatment

) {
}

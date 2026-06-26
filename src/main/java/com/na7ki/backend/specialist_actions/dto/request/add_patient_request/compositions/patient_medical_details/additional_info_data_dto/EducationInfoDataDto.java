package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.additional_info_data_dto;

import jakarta.validation.constraints.Size;

public record EducationInfoDataDto(

        @Size(max = 70, message = "school name shouldn't exceed 70 characters")
        String schoolName,

        @Size(max = 30, message = "scholastic level shouldn't exceed 30 characters")
        String scholasticLevel

) {
}

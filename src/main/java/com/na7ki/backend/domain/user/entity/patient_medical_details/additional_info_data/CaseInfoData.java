package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Embeddable
public record CaseInfoData(

    @NotNull(message = "date of beginning of treatment is required")
    @JsonFormat(pattern = "yyyy-M-d")
    @Column (nullable = false, updatable = false)
    LocalDate startDate,

    @NotBlank(message = "primary diagnosis is required")
    @Size(max = 300, message = "can't accept more than 300 characters")
    @Column (nullable = false, length = 300)
    String primaryDiagnosis,

    @Size(max = 1000, message = "can't accept more than 1000 characters")
    @Column (nullable = false, length = 1000)
    String notes,

    @NotNull(message = "required data")
    @Column (nullable = false)
    Boolean hasPreviousTreatment

) {
}

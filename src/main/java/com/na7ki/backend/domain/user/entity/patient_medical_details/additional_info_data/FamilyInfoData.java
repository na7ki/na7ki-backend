package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Embeddable
public record FamilyInfoData(

    @NotNull(message = "number of siblings is required")
    @PositiveOrZero
    @Column (nullable = false)
    Short noSiblings,

    @NotNull(message = "order in siblings is required")
    @PositiveOrZero
    @Column (nullable = false)
    Short orderInSiblings

) {
}

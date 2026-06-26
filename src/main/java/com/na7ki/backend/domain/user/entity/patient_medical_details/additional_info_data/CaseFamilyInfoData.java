package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CaseFamilyInfoData(

    @Column (nullable = false)
    Short noSiblings,

    @Column (nullable = false)
    Short orderInSiblings

) {
}

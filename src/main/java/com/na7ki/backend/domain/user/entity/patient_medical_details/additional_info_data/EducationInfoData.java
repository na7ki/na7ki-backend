package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record EducationInfoData(

        @Column (nullable = false, length = 70)
        String schoolName,

        @Column (nullable = false, length = 30)
        String scholasticLevel

) {
}

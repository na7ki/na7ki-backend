package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public record EducationInfoData(

        @Size(max = 70, message = "school name shouldn't exceed 70 characters")
        @Column (nullable = false, length = 70)
        String schoolName,

        @Size(max = 30, message = "scholastic level shouldn't exceed 30 characters")
        @Column (nullable = false, length = 30)
        String scholasticLevel

) {
}

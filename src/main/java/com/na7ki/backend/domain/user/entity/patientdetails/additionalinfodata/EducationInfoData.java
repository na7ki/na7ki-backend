package com.na7ki.backend.domain.user.entity.patientdetails.additionalinfodata;

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

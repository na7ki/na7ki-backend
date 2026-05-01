package com.na7ki.backend.auth.entity.patientdetails.additionalinfodata;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record AdditionalInfoData(

    @Embedded
    CaseInfoData data1,

    @Embedded
    CaseFamilyInfoData data2,

    @Embedded
    EducationInfoData data3

) {
}

package com.na7ki.backend.domain.user.entity.patient_details.additional_info_data;

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

package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record AdditionalInfoData(

    @Embedded
    CaseInfoData caseInfoData,

    @Embedded
    FamilyInfoData familyInfoData,

    @Embedded
    EducationInfoData educationInfoData

) {
}

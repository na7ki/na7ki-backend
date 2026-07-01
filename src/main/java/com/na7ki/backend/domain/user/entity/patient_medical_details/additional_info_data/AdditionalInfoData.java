package com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record AdditionalInfoData(

    @Embedded @NotNull(message = "case info is required")       @Valid CaseInfoData caseInfoData,

    @Embedded @NotNull(message = "family info is required")     @Valid FamilyInfoData familyInfoData,

    @Embedded @NotNull(message = "education info is required")  @Valid EducationInfoData educationInfoData

) {
}

package com.na7ki.backend.domain.user.entity.patientdetails.additionalinfodata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record CaseInfoData(

    @Column (nullable = false)
    LocalDate startDate,

    @Column (nullable = false)
    LocalDate endDate,

    @Column (nullable = false, length = 300)
    String primaryDiagnosis,

    @Column (nullable = false, length = 1000)
    String notes,

    @Column (nullable = false)
    Boolean hasPreviousTreatment

) {
}

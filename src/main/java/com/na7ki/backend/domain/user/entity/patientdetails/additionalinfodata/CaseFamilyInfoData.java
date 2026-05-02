package com.na7ki.backend.domain.user.entity.patientdetails.additionalinfodata;

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

package com.na7ki.backend.domain.user.entity.patientdetails.languageandcognitionevaluationdata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record LanguageEvaluationData(

        @Column(nullable = false)
        Short expressiveness,

        @Column (nullable = false)
        Short Receptiveness,

        @Column (nullable = false)
        Short internalLanguageScore

) {
}

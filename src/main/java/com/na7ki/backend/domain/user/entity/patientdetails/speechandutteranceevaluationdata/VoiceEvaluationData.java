package com.na7ki.backend.domain.user.entity.patientdetails.speechandutteranceevaluationdata;

import com.na7ki.backend.domain.user.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.Degree;
import com.na7ki.backend.domain.user.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.DysphoniaDegree;
import com.na7ki.backend.domain.user.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.VoiceQuality;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record VoiceEvaluationData(

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        Degree pitch,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        Degree intensity,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        VoiceQuality quality,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        DysphoniaDegree dysphoniaDegree

) {
}

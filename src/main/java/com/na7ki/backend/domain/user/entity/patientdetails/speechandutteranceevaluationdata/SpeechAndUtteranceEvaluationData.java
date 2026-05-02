package com.na7ki.backend.domain.user.entity.patientdetails.speechandutteranceevaluationdata;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record SpeechAndUtteranceEvaluationData(

        @Embedded
        VoiceEvaluationData data1,

        @Embedded
        SpeechCharacteristicsData data2

) {
}

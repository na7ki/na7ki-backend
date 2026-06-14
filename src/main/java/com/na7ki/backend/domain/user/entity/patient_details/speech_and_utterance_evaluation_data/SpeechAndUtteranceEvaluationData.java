package com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data;

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

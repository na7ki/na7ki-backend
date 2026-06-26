package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record SpeechAndUtteranceEvaluationData(

        @Embedded
        VoiceEvaluationData voiceEvaluationData,

        @Embedded
        SpeechCharacteristicsData speechCharacteristicsData

) {
}

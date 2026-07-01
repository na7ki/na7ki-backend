package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record SpeechAndUtteranceEvaluationData(

        @Embedded @NotNull(message = "voice evaluation data is required") @Valid        VoiceEvaluationData voiceEvaluationData,

        @Embedded @NotNull(message = "speech characteristics data is required") @Valid  SpeechCharacteristicsData speechCharacteristicsData

) {
}

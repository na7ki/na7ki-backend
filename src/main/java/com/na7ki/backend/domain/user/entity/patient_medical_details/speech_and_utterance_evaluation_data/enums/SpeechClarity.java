package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpeechClarity {
    LOW, MEDIUM, HIGH;

    @JsonCreator
    public static SpeechClarity fromString(String value) {
        return SpeechClarity.valueOf(value.trim().toUpperCase());
    }
}

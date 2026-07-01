package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpeechResonance {
    NORMAL, NASAL, ORAL;

    @JsonCreator
    public static SpeechResonance fromString(String value) {
        return SpeechResonance.valueOf(value.trim().toUpperCase());
    }
}

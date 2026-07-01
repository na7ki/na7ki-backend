package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpeechRate {
    SLOW, NORMAL, FAST;

    @JsonCreator
    public static SpeechRate fromString(String value) {
        return SpeechRate.valueOf(value.trim().toUpperCase());
    }
}

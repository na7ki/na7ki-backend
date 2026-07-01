package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SpeechFluency {
    NORMAL, STUTTERED, INTERRUPTED;

    @JsonCreator
    public static SpeechFluency fromString(String value) {
        return SpeechFluency.valueOf(value.trim().toUpperCase());
    }
}

package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VoiceQuality {
    PURE, HOARSE, BREATHY, MUFFLED;

    @JsonCreator
    public static VoiceQuality fromString(String value) {
        return VoiceQuality.valueOf(value.trim().toUpperCase());
    }
}

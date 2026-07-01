package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Degree {
    LOW, NORMAL, HIGH;

    @JsonCreator
    public static Degree fromString(String value) {
        return Degree.valueOf(value.trim().toUpperCase());
    }
}

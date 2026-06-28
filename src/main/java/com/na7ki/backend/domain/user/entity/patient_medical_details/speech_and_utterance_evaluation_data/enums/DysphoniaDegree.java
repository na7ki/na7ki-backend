package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DysphoniaDegree {
    ZERO, ONE, TWO, THREE;

    @JsonCreator
    public static DysphoniaDegree fromString(String value) {
        return DysphoniaDegree.valueOf(value.trim().toUpperCase());
    }
}

package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data;

import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.Degree;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.DysphoniaDegree;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.VoiceQuality;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record VoiceEvaluationData(

        @Enumerated(EnumType.STRING)
        @Column (nullable = false,  length = 20, updatable = false)
        Degree pitch,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        Degree intensity,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        VoiceQuality voiceQuality,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        DysphoniaDegree dysphoniaDegree

) {
}

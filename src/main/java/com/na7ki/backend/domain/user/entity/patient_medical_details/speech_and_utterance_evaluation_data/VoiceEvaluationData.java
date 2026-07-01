package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data;

import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.Degree;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.DysphoniaDegree;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.VoiceQuality;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record VoiceEvaluationData(

        @NotNull(message = "pitch degree is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false,  length = 20, updatable = false)
        Degree pitch,

        @NotNull(message = "intensity degree is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        Degree intensity,

        @NotNull(message = "voice quality degree is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        VoiceQuality voiceQuality,

        @NotNull(message = "dysphonia degree is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        DysphoniaDegree dysphoniaDegree

) {
}

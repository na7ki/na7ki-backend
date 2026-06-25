package com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data;

import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.Degree;
import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.DysphoniaDegree;
import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.VoiceQuality;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record VoiceEvaluationData(

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        Degree pitch,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        Degree intensity,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        VoiceQuality quality,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        DysphoniaDegree dysphoniaDegree

) {
}

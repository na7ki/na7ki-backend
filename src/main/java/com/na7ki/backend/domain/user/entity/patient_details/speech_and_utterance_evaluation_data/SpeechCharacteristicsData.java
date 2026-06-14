package com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data;

import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.SpeechClarity;
import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.SpeechFluency;
import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.SpeechSpeed;
import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.auxiliary.SpeechVibration;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record SpeechCharacteristicsData(

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        SpeechSpeed speechSpeed,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        SpeechFluency speechFluency,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        SpeechVibration speechVibration,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        SpeechClarity speechClarity

) {
}

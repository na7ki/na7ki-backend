package com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data;

import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechClarity;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechFluency;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechRate;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechResonance;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record SpeechCharacteristicsData(

        @NotNull(message = "speech rate class score is required")
        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20, updatable = false)
        SpeechRate speechRate,

        @NotNull(message = "speech fluency class is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        SpeechFluency speechFluency,

        @NotNull(message = "speech resonance class is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        SpeechResonance speechResonance,

        @NotNull(message = "speech clarity class is required")
        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20, updatable = false)
        SpeechClarity speechClarity

) {
}

package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.speech_and_utterance_evaluation_data_dto;

import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechClarity;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechFluency;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechRate;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.SpeechResonance;
import jakarta.validation.constraints.NotNull;

public record SpeechCharacteristicsDataDto(

        @NotNull(message = "speech rate class score is required")
        SpeechRate speechRate,

        @NotNull(message = "speech fluency class is required")
        SpeechFluency speechFluency,

        @NotNull(message = "speech resonance class is required")
        SpeechResonance speechResonance,

        @NotNull(message = "speech clarity class is required")
        SpeechClarity speechClarity

) {
}

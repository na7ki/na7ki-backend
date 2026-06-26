package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.speech_and_utterance_evaluation_data_dto;

import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.Degree;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.DysphoniaDegree;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.enums.VoiceQuality;
import jakarta.validation.constraints.NotNull;

public record VoiceEvaluationDataDto(

        @NotNull(message = "pitch degree is required")
        Degree pitch,

        @NotNull(message = "intensity degree is required")
        Degree intensity,

        @NotNull(message = "voice quality degree is required")
        VoiceQuality voiceQuality,

        @NotNull(message = "dysphonia degree is required")
        DysphoniaDegree dysphoniaDegree

) {
}

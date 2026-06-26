package com.na7ki.backend.domain.user.entity.patient_medical_details;

import com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data.CongnitionAndLanguageEvalutaionData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.SpeechAndUtteranceEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.AdditionalInfoData;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record PatientMedicalDetails(

    @Embedded
    AdditionalInfoData additionalInfoData,

    @Embedded
    SpeechAndUtteranceEvaluationData speechAndUtteranceEvaluationData,

    @Embedded
    CongnitionAndLanguageEvalutaionData congnitionAndLanguageEvalutaionData

) {
}

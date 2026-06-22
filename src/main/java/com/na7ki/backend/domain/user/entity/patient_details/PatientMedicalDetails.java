package com.na7ki.backend.domain.user.entity.patient_details;

import com.na7ki.backend.domain.user.entity.patient_details.language_and_cognition_evaluation_data.LanguageAndCognitionEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_details.speech_and_utterance_evaluation_data.SpeechAndUtteranceEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_details.additional_info_data.AdditionalInfoData;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record PatientMedicalDetails(

    @Embedded
    AdditionalInfoData data1,

    @Embedded
    LanguageAndCognitionEvaluationData data2,

    @Embedded
    SpeechAndUtteranceEvaluationData data3

) {
}

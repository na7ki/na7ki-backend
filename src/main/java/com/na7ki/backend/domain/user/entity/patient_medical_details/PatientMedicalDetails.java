package com.na7ki.backend.domain.user.entity.patient_medical_details;

import com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data.CongnitionAndLanguageEvalutaionData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.SpeechAndUtteranceEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.AdditionalInfoData;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Embeddable
public record PatientMedicalDetails(

    @Min(0)
    @Max(200)
    @Digits(integer= 3, fraction = 0)
    @Column(nullable = false, updatable = false)
    Short iq,

    @Embedded
    AdditionalInfoData additionalInfoData,

    @Embedded
    SpeechAndUtteranceEvaluationData speechAndUtteranceEvaluationData,

    @Embedded
    CongnitionAndLanguageEvalutaionData congnitionAndLanguageEvalutaionData

) {
}

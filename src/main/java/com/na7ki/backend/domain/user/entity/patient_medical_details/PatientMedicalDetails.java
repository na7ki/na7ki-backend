package com.na7ki.backend.domain.user.entity.patient_medical_details;

import com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data.CognitionAndLanguageEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.SpeechAndUtteranceEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.AdditionalInfoData;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record PatientMedicalDetails(

    @NotNull(message = "iq is required")
    @Min(value = 0, message = "IQ must be between 0 and 200")
    @Max(value = 200, message = "IQ must be between 0 and 200")
    @Digits(integer= 3, fraction = 0, message = "IQ is a whole number. It can't have fractions")
    @Column(nullable = false, updatable = false)
    Short iq,

    @Embedded @NotNull(message = "Additional info is required") @Valid                          AdditionalInfoData additionalInfoData,

    @Embedded @NotNull(message = "speech and utterance evaluation data are required") @Valid    SpeechAndUtteranceEvaluationData speechAndUtteranceEvaluationData,

    @Embedded @NotNull(message = "cognition and language evaluation data are required") @Valid CognitionAndLanguageEvaluationData cognitionAndLanguageEvaluationData

) {
}

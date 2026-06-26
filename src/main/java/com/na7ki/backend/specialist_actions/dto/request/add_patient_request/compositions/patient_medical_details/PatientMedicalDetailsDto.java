package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details;

import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.additional_info_data_dto.FamilyInfoDataDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.additional_info_data_dto.CaseInfoDataDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.additional_info_data_dto.EducationInfoDataDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.congnition_and_language_evalutaion_data_dto.LanguageAwarenessDataDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.congnition_and_language_evalutaion_data_dto.LanguageEvaluationDataDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.speech_and_utterance_evaluation_data_dto.SpeechCharacteristicsDataDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.speech_and_utterance_evaluation_data_dto.VoiceEvaluationDataDto;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PatientMedicalDetailsDto(

        @Min(value = 0, message = "IQ must be between 0 and 200")
        @Max(value = 200, message = "IQ must be between 0 and 200")
        @Digits(integer= 3, fraction = 0, message = "IQ is a whole number. It can't have fractions")
        @Column(nullable = false, updatable = false)
        Short iq,

        @NotNull(message = "case info data is required")                @Valid CaseInfoDataDto caseInfoDataDto,
        @NotNull(message = "family info data is required")              @Valid FamilyInfoDataDto familyInfoDataDto,
        @NotNull(message = "education info data is required")           @Valid EducationInfoDataDto educationInfoDataDto,

        @NotNull(message = "voice evaluation data is required")         @Valid VoiceEvaluationDataDto voiceEvaluationDataDto,
        @NotNull(message = "speech characteristics data is required")   @Valid SpeechCharacteristicsDataDto speechCharacteristicsDataDto,

        @NotNull(message = "language evaluation data is required")      @Valid LanguageEvaluationDataDto languageEvaluationDataDto,
        @NotNull(message = "language awareness data is required")       @Valid LanguageAwarenessDataDto languageAwarenessDataDto

) {
}

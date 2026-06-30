package com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response;

import com.na7ki.backend.domain.user.entity.patient_medical_details.additional_info_data.CaseInfoData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.cognition_and_language_evaluation_data.CognitionAndLanguageEvaluationData;
import com.na7ki.backend.domain.user.entity.patient_medical_details.speech_and_utterance_evaluation_data.SpeechAndUtteranceEvaluationData;

public record MedicalData(

        CaseInfoData caseInfoData,
        CognitionAndLanguageEvaluationData cognitionAndLanguageEvaluationData,
        SpeechAndUtteranceEvaluationData speechAndUtteranceEvaluationData

) {
}

package com.na7ki.backend.auth.entity.patientdetails;

import com.na7ki.backend.auth.entity.patientdetails.languageandcognitionevaluationdata.LanguageAndCognitionEvaluationData;
import com.na7ki.backend.auth.entity.patientdetails.speechandutteranceevaluationdata.SpeechAndUtteranceEvaluationData;
import com.na7ki.backend.auth.entity.patientdetails.additionalinfodata.AdditionalInfoData;
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

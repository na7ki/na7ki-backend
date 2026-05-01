package com.na7ki.backend.auth.entity.patientdetails.speechandutteranceevaluationdata;

import com.na7ki.backend.auth.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.SpeechClarity;
import com.na7ki.backend.auth.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.SpeechFluency;
import com.na7ki.backend.auth.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.SpeechSpeed;
import com.na7ki.backend.auth.entity.patientdetails.speechandutteranceevaluationdata.auxiliary.SpeechVibration;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record SpeechCharacteristicsData(

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        SpeechSpeed speechSpeed,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        SpeechFluency speechFluency,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        SpeechVibration speechVibration,

        @Enumerated(EnumType.STRING)
        @Column (nullable = false, length = 20)
        SpeechClarity speechClarity

) {
}

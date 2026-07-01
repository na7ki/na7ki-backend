package com.na7ki.backend.domain.user.model.create_patient;

import com.na7ki.backend.domain.user.entity.patient_medical_details.PatientMedicalDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreatePatientData(

        @NotNull(message = "user details are required")             @Valid UserDetailsData userDetailsData,
        @NotNull(message = "patient medical details are required")  @Valid PatientMedicalDetails medicalDetails

) {
}

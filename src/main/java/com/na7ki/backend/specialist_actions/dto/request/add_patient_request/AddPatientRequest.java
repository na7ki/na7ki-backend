package com.na7ki.backend.specialist_actions.dto.request.add_patient_request;

import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.UserDetailsDto;
import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions.patient_medical_details.PatientMedicalDetailsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AddPatientRequest(

        @NotNull(message = "user details are required")             @Valid UserDetailsDto userDetailsDto,
        @NotNull(message = "patient medical details are required")  @Valid PatientMedicalDetailsDto patientMedicalDetailsDto

) {
}

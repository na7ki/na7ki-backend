package com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response;

public record PatientDataResponse(

        UserData userData,
        MedicalData medicalData

) {
}

package com.na7ki.backend.core.email.model;

public record PatientPasswordEmail(

        String patientName,
        String associatedSpecialistName,
        String rawPassword

) {
}

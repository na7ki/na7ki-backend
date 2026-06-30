package com.na7ki.backend.specialist_actions.manage_patients.exception;

public class SpecialistRequestingNonAssociatedPatientDataException extends RuntimeException {
    public SpecialistRequestingNonAssociatedPatientDataException(String message) {
        super(message);
    }
}

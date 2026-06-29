package com.na7ki.backend.specialist_actions.exception;

public class SpecialistRequestingNonAssociatedPatientDataException extends RuntimeException {
    public SpecialistRequestingNonAssociatedPatientDataException(String message) {
        super(message);
    }
}

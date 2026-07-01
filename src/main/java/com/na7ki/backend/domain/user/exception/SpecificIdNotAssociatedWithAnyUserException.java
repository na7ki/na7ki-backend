package com.na7ki.backend.domain.user.exception;

public class SpecificIdNotAssociatedWithAnyUserException extends RuntimeException {
    public SpecificIdNotAssociatedWithAnyUserException(String message) {
        super(message);
    }
}

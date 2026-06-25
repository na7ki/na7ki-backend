package com.na7ki.backend.domain.user.verification_code.exception;

public class VerificationCodeForThisEmailAlreadyExistsException extends RuntimeException {
    public VerificationCodeForThisEmailAlreadyExistsException(String message) {
        super(message);
    }
}

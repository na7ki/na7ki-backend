package com.na7ki.backend.auth.verificationcode.exception;

public class VerificationCodeForThisEmailAlreadyExistsException extends RuntimeException {
    public VerificationCodeForThisEmailAlreadyExistsException(String message) {
        super(message);
    }
}

package com.na7ki.backend.auth.verificationcode.exception;

public class VerificationCodeMismatchException extends RuntimeException {
    public VerificationCodeMismatchException(String message) {
        super(message);
    }
}

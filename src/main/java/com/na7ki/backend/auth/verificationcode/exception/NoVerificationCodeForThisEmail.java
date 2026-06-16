package com.na7ki.backend.auth.verificationcode.exception;

public class NoVerificationCodeForThisEmail extends RuntimeException {
    public NoVerificationCodeForThisEmail(String message) {
        super(message);
    }
}

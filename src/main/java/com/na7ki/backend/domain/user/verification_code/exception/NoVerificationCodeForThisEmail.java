package com.na7ki.backend.domain.user.verification_code.exception;

public class NoVerificationCodeForThisEmail extends RuntimeException {
    public NoVerificationCodeForThisEmail(String message) {
        super(message);
    }
}

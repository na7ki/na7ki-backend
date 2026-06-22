package com.na7ki.backend.domain.user.verification_code.exception;

public class NonExistentUserResetsPasswordException extends RuntimeException {
    public NonExistentUserResetsPasswordException(String message) {
        super(message);
    }
}

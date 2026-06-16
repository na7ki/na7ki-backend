package com.na7ki.backend.domain.user.exception;

public class PhoneNumberNotUniqueException extends RuntimeException {
    public PhoneNumberNotUniqueException(String message) {
        super(message);
    }
}

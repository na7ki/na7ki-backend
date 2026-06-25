package com.na7ki.backend.auth.exception;

public class PhoneNumberNotUniqueException extends RuntimeException {
    public PhoneNumberNotUniqueException(String message) {
        super(message);
    }
}

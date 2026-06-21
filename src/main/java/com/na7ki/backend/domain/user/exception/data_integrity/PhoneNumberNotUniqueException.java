package com.na7ki.backend.domain.user.exception.data_integrity;

public class PhoneNumberNotUniqueException extends RuntimeException {
    public PhoneNumberNotUniqueException(String message) {
        super(message);
    }
}

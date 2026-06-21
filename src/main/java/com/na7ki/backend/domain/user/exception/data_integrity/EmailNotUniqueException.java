package com.na7ki.backend.domain.user.exception.data_integrity;

public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException(String message) {
        super(message);
    }
}

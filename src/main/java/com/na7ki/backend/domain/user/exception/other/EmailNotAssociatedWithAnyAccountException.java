package com.na7ki.backend.domain.user.exception.other;

public class EmailNotAssociatedWithAnyAccountException extends RuntimeException {
    public EmailNotAssociatedWithAnyAccountException(String message) {
        super(message);
    }
}

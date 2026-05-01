package com.na7ki.backend.auth.exception;

public class EmailNotAssociatedWithAnyAccountException extends RuntimeException {
    public EmailNotAssociatedWithAnyAccountException(String message) {
        super(message);
    }
}

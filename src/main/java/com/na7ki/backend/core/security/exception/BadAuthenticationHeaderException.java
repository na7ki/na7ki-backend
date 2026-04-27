package com.na7ki.backend.core.security.exception;

public class BadAuthenticationHeaderException extends RuntimeException {

    public BadAuthenticationHeaderException(String message) {
        super(message);
    }

}

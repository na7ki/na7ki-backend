package com.na7ki.backend.domain.user.exception;

public class IdNotAssociatedWithAnyUserException extends RuntimeException {
    public IdNotAssociatedWithAnyUserException(String message) {
        super(message);
    }
}

package com.na7ki.backend.domain.user.exception.other;

public class UnknownRoleException extends RuntimeException {
    public UnknownRoleException(String message) {
        super(message);
    }
}

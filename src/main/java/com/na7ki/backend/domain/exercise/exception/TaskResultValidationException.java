package com.na7ki.backend.domain.exercise.exception;

import java.util.List;

public class TaskResultValidationException extends RuntimeException {

    private final List<String> errors;

    public TaskResultValidationException(List<String> errors) {
        super(String.join("; ", errors));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

package com.na7ki.backend.domain.exercise.pronunciation.exception;

import java.util.List;

public class PronunciationValidationException extends RuntimeException {

    private final List<String> errors;

    public PronunciationValidationException(List<String> errors) {
        super(String.join("; ", errors));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

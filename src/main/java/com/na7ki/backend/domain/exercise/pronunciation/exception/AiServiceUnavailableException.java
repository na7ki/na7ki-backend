package com.na7ki.backend.domain.exercise.pronunciation.exception;

/**
 * Thrown when the na7ki-ai service can't be reached, times out, or reports
 * it hasn't finished loading its checkpoint (its own /verify returns 503).
 */
public class AiServiceUnavailableException extends RuntimeException {

    public AiServiceUnavailableException(String message) {
        super(message);
    }

    public AiServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

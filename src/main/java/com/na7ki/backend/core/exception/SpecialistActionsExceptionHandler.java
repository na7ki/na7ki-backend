package com.na7ki.backend.core.exception;

import com.na7ki.backend.specialist_actions.manage_patients.exception.SpecialistRequestingNonAssociatedPatientDataException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecialistActionsExceptionHandler {

    @ExceptionHandler(SpecialistRequestingNonAssociatedPatientDataException.class)
    public ResponseEntity<String> handleSpecialistRequestingNonAssociatedPatientDataException(SpecialistRequestingNonAssociatedPatientDataException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}

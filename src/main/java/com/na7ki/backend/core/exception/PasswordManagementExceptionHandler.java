package com.na7ki.backend.core.exception;

import com.na7ki.backend.domain.user.verification_code.exception.InvalidVerificationCodeException;
import com.na7ki.backend.domain.user.verification_code.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.domain.user.verification_code.exception.NonExistentUserResetsPasswordException;
import com.na7ki.backend.domain.user.verification_code.exception.VerificationCodeForThisEmailAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PasswordManagementExceptionHandler {

    @ExceptionHandler(NoVerificationCodeForThisEmail.class)
    public ResponseEntity<String> handleNoVerificationCode(NoVerificationCodeForThisEmail ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VerificationCodeForThisEmailAlreadyExistsException.class)
    public ResponseEntity<String> handleVerificationCodeAlreadyExists(VerificationCodeForThisEmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentUserResetsPasswordException.class)
    public ResponseEntity<String> handleNonExistentUserResetsPasswordException(NonExistentUserResetsPasswordException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<String> handleInvalidVerificationCodeException(InvalidVerificationCodeException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(ex.getMessage());
    }

}

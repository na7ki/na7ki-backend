package com.na7ki.backend.core.exception;

import com.na7ki.backend.domain.user.exception.EmailNotAssociatedWithAnyAccountException;
import com.na7ki.backend.domain.user.exception.EmailNotUniqueException;
import com.na7ki.backend.domain.user.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.domain.user.exception.UnknownRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<String> handleEmailNotUnique(EmailNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberNotUniqueException.class)
    public ResponseEntity<String> handlePhoneNoNotUnique(PhoneNumberNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmailNotAssociatedWithAnyAccountException.class)
    public ResponseEntity<String> handleAccountNotFound(EmailNotAssociatedWithAnyAccountException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnknownRoleException.class)
    public ResponseEntity<String> handleUnknownRole(UnknownRoleException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}

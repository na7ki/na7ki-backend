package com.na7ki.backend.core.exception;

import com.na7ki.backend.core.security.exception.InvalidJwtTokenException;
import com.na7ki.backend.domain.user.exception.AccountNotActiveException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<String> handleInvalidJwtTokenException(InvalidJwtTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired or invalid token");
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<String> handleAccountNotActiveException(AccountNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account is deactivated");
    }

}

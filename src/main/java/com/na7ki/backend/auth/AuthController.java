package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.LoginRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.domain.user.exception.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping("/register-specialist")
    public ResponseEntity<AuthResponse> specialistRegister(@Valid @RequestBody SpecialistRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerSpecialist(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Stateless JWT — nothing to invalidate server-side.
        // Client is responsible for deleting the stored token.
        return ResponseEntity.noContent().build();
    }





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

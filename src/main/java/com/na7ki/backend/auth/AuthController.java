package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.login.LoginRequest;
import com.na7ki.backend.auth.dto.request.register.PatientRegisterRequest;
import com.na7ki.backend.auth.dto.request.register.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.auth.exception.EmailNotUniqueException;
import com.na7ki.backend.auth.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.auth.exception.UnknownRoleException;
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



    @PostMapping("/register/specialist")
    public ResponseEntity<AuthResponse> specialistRegister(@Valid @RequestBody SpecialistRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerSpecialist(request));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<AuthResponse> patientRegister(@Valid @RequestBody PatientRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerPatient(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }





    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<String> handleEmailNotUnique(EmailNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberNotUniqueException.class)
    public ResponseEntity<String> handlePhoneNoNotUnique(PhoneNumberNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UnknownRoleException.class)
    public ResponseEntity<String> handleUnknownRole(UnknownRoleException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}

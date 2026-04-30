package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.login.LoginRequest;
import com.na7ki.backend.auth.dto.request.register.PatientRegisterRequest;
import com.na7ki.backend.auth.dto.request.register.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

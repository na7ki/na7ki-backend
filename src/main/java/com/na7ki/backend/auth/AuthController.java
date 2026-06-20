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

}

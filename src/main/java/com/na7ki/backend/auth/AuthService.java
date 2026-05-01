package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.login.LoginRequest;
import com.na7ki.backend.auth.dto.request.register.PatientRegisterRequest;
import com.na7ki.backend.auth.dto.request.register.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.auth.entity.Patient;
import com.na7ki.backend.auth.entity.Specialist;
import com.na7ki.backend.auth.entity.User;
import com.na7ki.backend.auth.exception.EmailNotUniqueException;
import com.na7ki.backend.auth.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.auth.exception.UnknownRoleException;
import com.na7ki.backend.auth.util.UserMapper;
import com.na7ki.backend.core.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;



    public AuthResponse registerSpecialist (SpecialistRegisterRequest request) {
        Specialist specialist = userMapper.toSpecialist(request);
        String jwt = registerUser(specialist, request.getPassword());
        return new AuthResponse(jwt, specialist.getEmail(), Collections.singletonList("SPECIALIST"));
    }

    public AuthResponse registerPatient (PatientRegisterRequest request) {
        Patient patient = userMapper.toPatient(request);
        String jwt = registerUser(patient, request.getPassword());
        return new AuthResponse(jwt, patient.getEmail(), Collections.singletonList("PATIENT"));
    }

    private String registerUser(User user, String password) {

        //check uniqueness of some credentials
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailNotUniqueException("This email is in use by another user");
        }
        if (userRepository.existsByAnyPhoneNumber(user.getPhoneNumbers())) {
            throw new PhoneNumberNotUniqueException("One of the phone numbers provided is in use by another user");
        }

        //persist user
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        //return jwt
        return jwtUtil.generateToken(user);

    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        String jwt = jwtUtil.generateToken(user);

        return switch (user.getRole()) {
            case "SPECIALIST" ->
                    new AuthResponse(jwt, user.getEmail(), Collections.singletonList("SPECIALIST"));
            case "PATIENT" ->
                    new AuthResponse(jwt, user.getEmail(), Collections.singletonList("PATIENT"));
            default ->
                    throw new UnknownRoleException("Unknown role: " + user.getRole());
        };
    }
}

package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.LoginRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.auth.entity.Specialist;
import com.na7ki.backend.auth.entity.User;
import com.na7ki.backend.auth.exception.*;
import com.na7ki.backend.auth.repository.SpecialistRepository;
import com.na7ki.backend.auth.repository.UserRepository;
import com.na7ki.backend.auth.util.UserMapper;
import com.na7ki.backend.core.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public AuthResponse registerSpecialist (SpecialistRegisterRequest request) {

        //check uniqueness of some credentials
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailNotUniqueException("This email is in use by another user");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new PhoneNumberNotUniqueException("This phone numbers is in use by another user");
        }

        Specialist specialist = userMapper.toSpecialist(request);
        manageSpecialistFields(specialist, request);

        specialistRepository.save(specialist);
        String jwt = jwtUtil.generateToken(specialist);

        return new AuthResponse(jwt, specialist.getEmail(), Collections.singletonList("SPECIALIST"));

    }

    private void manageSpecialistFields(Specialist specialist, SpecialistRegisterRequest request) {

        specialist.setPassword(passwordEncoder.encode(request.password()));
        specialist.setAge((byte) Period.between(request.dateOfBirth(), LocalDate.now()).getYears());
        specialist.setSpecialistID("SP" + (userRepository.countByType(Specialist.class) + 1));
        specialist.setDisplayImage_path("NOT SET YET");

        List<String> paths = new ArrayList<>();
        paths.add("NOT SET YET");
        specialist.setPersonalImages_paths(paths);

    }

    public AuthResponse login(LoginRequest request) {

        // check existence first, before authentication
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EmailNotAssociatedWithAnyAccountException("No account is associated with this email"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

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

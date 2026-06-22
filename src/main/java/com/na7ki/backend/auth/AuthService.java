package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.LoginRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.common.util.DateUtils;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.auth.util.UserMapper;
import com.na7ki.backend.core.security.jwt.JwtUtil;
import com.na7ki.backend.domain.user.exception.EmailNotUniqueException;
import com.na7ki.backend.domain.user.exception.PhoneNumberNotUniqueException;
import com.na7ki.backend.domain.user.exception.UnknownRoleException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthResponse registerSpecialist (SpecialistRegisterRequest request) {

        //check uniqueness of some credentials
        if (!userService.isEmailUnique(request.email())) {
            throw new EmailNotUniqueException("This email is in use by another user");
        }
        if (!userService.isPhoneNumberUnique(request.phoneNumber())) {
            throw new PhoneNumberNotUniqueException("This phone numbers is in use by another user");
        }

        Specialist specialist = userMapper.toSpecialist(request);
        manageSpecialistFields(specialist, request);

        userService.saveUser(specialist);

        String jwt = jwtUtil.generateToken(specialist);
        return new AuthResponse(jwt, specialist.getEmail(), Collections.singletonList("SPECIALIST"));

    }

    private void manageSpecialistFields(Specialist specialist, SpecialistRegisterRequest request) {
        userService.updateUserPassword(specialist, request.password());
        specialist.setAge(DateUtils.calculateAge(request.dateOfBirth()));
        specialist.setSpecialistID("SP" + userService.createSpecialistIdNumberPart());
    }

    public AuthResponse login(LoginRequest request) {

        User user = userService.findByEmailOrThrow(request.email());

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

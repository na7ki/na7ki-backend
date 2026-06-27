package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.LoginRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.auth.util.SpecialistRegisterMapper;
import com.na7ki.backend.common.util.DateUtils;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
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
    private final SpecialistRegisterMapper specialistRegisterMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthResponse registerSpecialist (SpecialistRegisterRequest request) {

        Specialist createdSpecialist = userService.createSpecialist(specialistRegisterMapper.toCreateSpecialistData(request));
        String jwt = jwtUtil.generateToken(createdSpecialist);
        return new AuthResponse(jwt, createdSpecialist.getEmail(), "SPECIALIST");
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

        return new AuthResponse(jwt, user.getEmail(), user.getRole());
    }
}

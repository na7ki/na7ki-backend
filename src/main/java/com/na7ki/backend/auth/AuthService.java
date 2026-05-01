package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.forgotpassword.ForgotPasswordRequest;
import com.na7ki.backend.auth.dto.request.LoginRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.request.forgotpassword.ResetPasswordRequest;
import com.na7ki.backend.auth.dto.request.forgotpassword.VerifyCodeRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.auth.dto.response.forgotpassword.ForgotPasswordResponse;
import com.na7ki.backend.auth.dto.response.forgotpassword.VerifyCodeResponse;
import com.na7ki.backend.auth.verificationcode.VerificationCodeService;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.auth.exception.*;
import com.na7ki.backend.domain.user.repository.SpecialistRepository;
import com.na7ki.backend.domain.user.repository.UserRepository;
import com.na7ki.backend.domain.user.UserMapper;
import com.na7ki.backend.core.email.EmailService;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final VerificationCodeService verificationCodeService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
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

    public ForgotPasswordResponse requestResetPassword (ForgotPasswordRequest request) {

        Optional<User> user = userRepository.findByEmail(request.email());
        if (user.isPresent()) {
            String resetVerificationCode = verificationCodeService.createCode(user.get());
            emailService.sendVerificationCode(request.email(), resetVerificationCode);
        }
        return new ForgotPasswordResponse(request.email(), "A verification code has been sent to this email");
    }

    public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {
        return new VerifyCodeResponse(request.email(), verificationCodeService.verifyCode(request.code(), request.email()));
    }

    public void resetPassword (ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EmailNotAssociatedWithAnyAccountException("No user is associated with this email"));

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }

}

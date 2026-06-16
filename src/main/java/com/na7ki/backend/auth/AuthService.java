package com.na7ki.backend.auth;

import com.na7ki.backend.auth.dto.request.forgotpassword.ForgotPasswordRequest;
import com.na7ki.backend.auth.dto.request.LoginRequest;
import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.dto.request.forgotpassword.ResendCodeRequest;
import com.na7ki.backend.auth.dto.request.forgotpassword.ResetPasswordRequest;
import com.na7ki.backend.auth.dto.request.forgotpassword.VerifyCodeRequest;
import com.na7ki.backend.auth.dto.response.AuthResponse;
import com.na7ki.backend.auth.dto.response.forgotpassword.ForgotPasswordResponse;
import com.na7ki.backend.auth.dto.response.forgotpassword.ResendCodeResponse;
import com.na7ki.backend.auth.dto.response.forgotpassword.VerifyCodeResponse;
import com.na7ki.backend.auth.verificationcode.VerificationCode;
import com.na7ki.backend.auth.verificationcode.VerificationCodeService;
import com.na7ki.backend.auth.verificationcode.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeForThisEmailAlreadyExistsException;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.auth.exception.*;
import com.na7ki.backend.auth.util.UserMapper;
import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
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

        specialist.setPassword(passwordEncoder.encode(request.password()));
        specialist.setAge((byte) Period.between(request.dateOfBirth(), LocalDate.now()).getYears());
        specialist.setSpecialistID("SP" + userService.createSpecialistIdNumberPart());
        specialist.setDisplayImage_path("NOT SET YET");

        List<String> paths = new ArrayList<>();
        paths.add("NOT SET YET");
        specialist.setPersonalImages_paths(paths);

    }

    public AuthResponse login(LoginRequest request) {

        // check existence first, before authentication
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

    @Transactional
    public ForgotPasswordResponse requestResetPassword (ForgotPasswordRequest request) {

        Optional<User> potentialUser= userService.findByEmail(request.email());
        if (potentialUser.isPresent()) {
            VerificationCode potentialAssociatedCode = potentialUser.get().getVerificationCode();
            if (potentialAssociatedCode != null)
            {
                if (potentialAssociatedCode.getExpiresAt().isAfter(LocalDateTime.now())) {
                    throw new VerificationCodeForThisEmailAlreadyExistsException("There is already a verification code for this email");
                } else {
                    verificationCodeService.deleteCode(potentialAssociatedCode);
                }
            }
            String verificationCode = verificationCodeService.createCode(potentialUser.get());
            emailService.sendVerificationCode(request.email(), verificationCode);
            return new ForgotPasswordResponse(request.email(), true, "A verification code has been sent to this email");
        } else {
            return new ForgotPasswordResponse(request.email(), false, "A verification code has been sent to this email");
        }
    }

    @Transactional
    public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {
        return new VerifyCodeResponse(request.email(), verificationCodeService.verifyCode(request.code(), request.email()));
    }

    @Transactional
    public ResendCodeResponse resendCode (ResendCodeRequest request) {
        VerificationCode potentialAssociatedCode = null;
        boolean isValid = true;
        try {
            potentialAssociatedCode = verificationCodeService.getCodeOfUser(request.email());
        } catch (NoVerificationCodeForThisEmail e) {
            isValid = false;
        } finally {
            if (potentialAssociatedCode != null && potentialAssociatedCode.getExpiresAt().isBefore(LocalDateTime.now())) {
                verificationCodeService.deleteCode(potentialAssociatedCode);
                isValid = false;
            }
            if (!isValid) {
                requestResetPassword(new ForgotPasswordRequest(request.email()));
                return new ResendCodeResponse(request.email(), true, "A new code has been sent to your email");
            }
            else {
                return new ResendCodeResponse(request.email(), false,"There's a code associated with this email. Please wait a few minutes before requesting another code");
            }
        }
    }

    @Transactional
    public void resetPassword (ResetPasswordRequest request) {
        User user = userService.findByEmailOrThrow(request.email());
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userService.saveUser(user);
    }

}

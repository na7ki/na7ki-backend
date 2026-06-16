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
import com.na7ki.backend.auth.exception.*;
import com.na7ki.backend.auth.verificationcode.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeExpiredException;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeForThisEmailAlreadyExistsException;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeMismatchException;
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





    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> requestResetPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.requestResetPassword(request));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<VerifyCodeResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.verifyCode(request));
    }

    @PostMapping("/resend-code")
    public ResponseEntity<ResendCodeResponse> resendCode(@Valid @RequestBody ResendCodeRequest request) {
        ResendCodeResponse response = authService.resendCode(request);
        if (response.isResent())
        {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }





    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<String> handleEmailNotUnique(EmailNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberNotUniqueException.class)
    public ResponseEntity<String> handlePhoneNoNotUnique(PhoneNumberNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(SpecialistPersonalImageReuseException.class)
    public ResponseEntity<String> handleSpecialistPersonalImageReuse(SpecialistPersonalImageReuseException ex) {
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





    @ExceptionHandler(NoVerificationCodeForThisEmail.class)
    public ResponseEntity<String> handleNoVerificationCode(NoVerificationCodeForThisEmail ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VerificationCodeExpiredException.class)
    public ResponseEntity<String> handleVerificationCodeExpired(VerificationCodeExpiredException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(ex.getMessage());
    }

    @ExceptionHandler(VerificationCodeForThisEmailAlreadyExistsException.class)
    public ResponseEntity<String> handleVerificationCodeAlreadyExists(VerificationCodeForThisEmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

    @ExceptionHandler(VerificationCodeMismatchException.class)
    public ResponseEntity<String> handleVerificationCodeMismatch(VerificationCodeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}

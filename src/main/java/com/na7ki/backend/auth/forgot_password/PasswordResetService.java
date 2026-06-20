package com.na7ki.backend.auth.forgot_password;

import com.na7ki.backend.auth.forgot_password.dto.request.ForgotPasswordRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.ResendCodeRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.VerifyCodeRequest;
import com.na7ki.backend.auth.forgot_password.dto.response.ForgotPasswordResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.ResendCodeResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.ResetPasswordResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.verification_code.VerificationCodeService;
import com.na7ki.backend.domain.user.verification_code.auxiliary.VerifyCodeStatus;
import com.na7ki.backend.domain.user.verification_code.exception.InvalidVerificationCodeException;
import com.na7ki.backend.domain.user.verification_code.exception.NonExistentUserResetsPasswordException;
import com.na7ki.backend.core.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;





    @Transactional
    public ForgotPasswordResponse requestResetPassword (ForgotPasswordRequest request) {

        Optional<User> potentialUser= userService.findByEmail(request.getEmail());

        if (potentialUser.isEmpty()) {
            return new ForgotPasswordResponse(request.getEmail(), false, "If this email is registered, a new code has been sent");
        }

        User user = potentialUser.get();

        String createdCode = verificationCodeService.requestCode(user, false);
        emailService.sendVerificationCode(request.getEmail(), createdCode);
        return new ForgotPasswordResponse(request.getEmail(), true, "If this email is registered, a new code has been sent");
    }

    @Transactional
    public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {
        VerifyCodeStatus status = verificationCodeService.verifyCode(request.getCode(), request.getEmail(), false);

        String message = "uninitialized message";
        switch (status) {
            case VerifyCodeStatus.MATCH -> message = "The code has been verified successfully";
            case VerifyCodeStatus.DOES_NOT_MATCH -> message = "The code you entered is incorrect. Try again";
            case VerifyCodeStatus.EXPIRED -> message = "The code has expired, please request a new code";
        }

        return new VerifyCodeResponse(request.getEmail(), status, message);
    }

    @Transactional
    public ResendCodeResponse resendCode (ResendCodeRequest request) {

        Optional<User> potentialUser= userService.findByEmail(request.getEmail());

        if (potentialUser.isEmpty()) {
            return new ResendCodeResponse(request.getEmail(), false, "If this email is registered, a new code has been sent. Any previous code is now deactivated");
        }

        User user = potentialUser.get();

        String recreatedCode = verificationCodeService.requestCode(user, true);
        emailService.sendVerificationCode(request.getEmail(), recreatedCode);
        return new ResendCodeResponse(request.getEmail(), true, "If this email is registered, a new code has been sent. Any previous code is now deactivated");
    }

    @Transactional
    public ResetPasswordResponse resetPassword (ResetPasswordRequest request) {

        Optional<User> potentialUser= userService.findByEmail(request.getEmail());

        if (potentialUser.isEmpty())
        {
            throw new NonExistentUserResetsPasswordException("A user not registered in the DB is trying to reset their password");
        }

        User user = potentialUser.get();

        VerifyCodeStatus status = verificationCodeService.verifyCode(request.getCode(), request.getEmail(), true);
        if (status != VerifyCodeStatus.MATCH)
        {
            throw new InvalidVerificationCodeException("invalid or expired verification code");
        }

        userService.updateUserPassword(user, request.getNewPassword());
        userService.saveUser(user);

        return new ResetPasswordResponse(request.getEmail(), "password reset successfully");
    }
}

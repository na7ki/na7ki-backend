package com.na7ki.backend.account_management.reset_password;

import com.na7ki.backend.account_management.reset_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.account_management.reset_password.dto.request.VerifyCodeRequest;
import com.na7ki.backend.account_management.reset_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.verification_code.VerificationCodeService;
import com.na7ki.backend.domain.user.verification_code.auxiliary.VerifyCodeStatus;
import com.na7ki.backend.domain.user.verification_code.exception.InvalidVerificationCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;


    @Transactional
    public void requestResetPassword(User user) {
        String createdCode = verificationCodeService.requestCode(user, false);
        emailService.sendVerificationCode(user.getEmail(), createdCode);
    }

    public void resendCode(User user) {
        String code = verificationCodeService.requestCode(user, true);
        emailService.sendVerificationCode(user.getEmail(), code);
    }

    public VerifyCodeResponse verifyCode(User user, VerifyCodeRequest request) {
        VerifyCodeStatus status = verificationCodeService.verifyCode(user, request.code(), false);

        String message = "uninitialized message";
        switch (status) {
            case VerifyCodeStatus.MATCH -> message = "The code has been verified successfully";
            case VerifyCodeStatus.DOES_NOT_MATCH -> message = "The code you entered is incorrect. Try again";
            case VerifyCodeStatus.EXPIRED -> message = "The code has expired, please request a new code";
        }

        return new VerifyCodeResponse(status, message);
    }

    public void resetPassword(User user, ResetPasswordRequest request) {

        VerifyCodeStatus status = verificationCodeService.verifyCode(user, request.code(), true);
        if (status != VerifyCodeStatus.MATCH)
        {
            throw new InvalidVerificationCodeException("invalid or expired verification code");
        }

        userService.updateUserPassword(user, request.newPassword());
        userService.saveUser(user);
    }

}

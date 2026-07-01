package com.na7ki.backend.auth.forgot_password;

import com.na7ki.backend.auth.forgot_password.dto.request.SendCodeRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.VerifyCodeRequest;
import com.na7ki.backend.auth.forgot_password.dto.response.SendCodeResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.ResetPasswordResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.verification_code.model.VerifyCodeResult;
import com.na7ki.backend.domain.user.verification_code.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.domain.user.verification_code.exception.NonExistentUserResetsPasswordException;
import com.na7ki.backend.domain.user.service.PasswordResetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Transactional
public class ForgotPasswordService {

    private final UserService userService;
    private final PasswordResetCodeService passwordResetCodeService;





    public SendCodeResponse sendCode (SendCodeRequest request, Boolean replaceExistingCode) {
        Optional<User> potentialUser= userService.findByEmail(request.getEmail());
        if (potentialUser.isEmpty()) {
            return new SendCodeResponse(request.getEmail(), false, "If this email is registered, a new code has been sent");
        }
        User user = potentialUser.get();

        passwordResetCodeService.sendCode(user, replaceExistingCode);
        return new SendCodeResponse(request.getEmail(), true, "If this email is registered, a new code has been sent");
    }

    public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {

        User user = getUserOfProvidedEmail(request.getEmail(),
                () -> new NoVerificationCodeForThisEmail("No verification code for this email. Please request a code"));

        VerifyCodeResult result = passwordResetCodeService.verifyCode(user, request.getCode(), false);
        return new VerifyCodeResponse(request.getEmail(), result.status(), result.message());
    }

    public ResetPasswordResponse resetPassword (ResetPasswordRequest request) {

        User user = getUserOfProvidedEmail(request.getEmail(),
                () -> new NonExistentUserResetsPasswordException("A user that's not registered in the DB is trying to reset their password"));

        passwordResetCodeService.resetPassword(user, request.getCode(), request.getNewPassword());
        return new ResetPasswordResponse(request.getEmail(), "password reset successfully");
    }

    private User getUserOfProvidedEmail (String email, Supplier<? extends RuntimeException> exceptionSupplier) {
        return userService.findByEmail(email).orElseThrow(exceptionSupplier);
    }

}

package com.na7ki.backend.account_management.reset_password;

import com.na7ki.backend.account_management.reset_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.account_management.reset_password.dto.request.VerifyCodeRequest;
import com.na7ki.backend.account_management.reset_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.verification_code.model.VerifyCodeResult;
import com.na7ki.backend.domain.user.PasswordResetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {

    private final PasswordResetCodeService passwordResetCodeService;





    public void sendCode(User user, Boolean replaceExistingCode) {
        passwordResetCodeService.sendCode(user, replaceExistingCode);
    }

    public VerifyCodeResponse verifyCode(User user, VerifyCodeRequest request) {
        VerifyCodeResult result = passwordResetCodeService.verifyCode(user, request.code(), false);
        return new VerifyCodeResponse(result.status(), result.message());
    }

    public void resetPassword(User user, ResetPasswordRequest request) {
        passwordResetCodeService.resetPassword(user, request.code(), request.newPassword());
    }

}

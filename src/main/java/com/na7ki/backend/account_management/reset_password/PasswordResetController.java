package com.na7ki.backend.account_management.reset_password;

import com.na7ki.backend.account_management.reset_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.account_management.reset_password.dto.request.VerifyCodeRequest;
import com.na7ki.backend.account_management.reset_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/change-password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;





    @PostMapping("/request-code")
    public ResponseEntity<Void> requestResetPassword(@AuthenticationPrincipal User user) {
        passwordResetService.sendCode(user, false);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resend-code")
    public ResponseEntity<Void> resendCode(@AuthenticationPrincipal User user) {
        passwordResetService.sendCode(user, true);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<VerifyCodeResponse> verifyCode(
            @Valid @RequestBody VerifyCodeRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(passwordResetService.verifyCode(user, request));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request,
            @AuthenticationPrincipal User user) {
        passwordResetService.resetPassword(user, request);
        return ResponseEntity.noContent().build();
    }

}

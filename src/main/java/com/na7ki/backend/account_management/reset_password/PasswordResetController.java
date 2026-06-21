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
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request-code")
    public ResponseEntity<Void> requestCode(@AuthenticationPrincipal User user) {
        passwordResetService.requestResetPassword(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resend-code")
    public ResponseEntity<Void> resendCode(@AuthenticationPrincipal User user) {
        passwordResetService.resendCode(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<VerifyCodeResponse> verifyCode(
            @Valid @RequestBody VerifyCodeRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(passwordResetService.verifyCode(user, request));
    }

    @PatchMapping
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request,
            @AuthenticationPrincipal User user) {
        passwordResetService.resetPassword(user, request);
        return ResponseEntity.noContent().build();
    }

}

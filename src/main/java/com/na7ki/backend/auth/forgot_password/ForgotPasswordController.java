package com.na7ki.backend.auth.forgot_password;

import com.na7ki.backend.auth.forgot_password.dto.response.SendCodeResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.ResetPasswordResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.auth.forgot_password.dto.request.SendCodeRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.VerifyCodeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;





    @PostMapping("/request-code")
    public ResponseEntity<SendCodeResponse> requestResetPassword(@Valid @RequestBody SendCodeRequest request) {
        return ResponseEntity.ok(forgotPasswordService.sendCode(request, true));
    }

    @PostMapping("/resend-code")
    public ResponseEntity<SendCodeResponse> resendCode(@Valid @RequestBody SendCodeRequest request) {
        return ResponseEntity.ok(forgotPasswordService.sendCode(request, true));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<VerifyCodeResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(forgotPasswordService.verifyCode(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(forgotPasswordService.resetPassword(request));
    }

}

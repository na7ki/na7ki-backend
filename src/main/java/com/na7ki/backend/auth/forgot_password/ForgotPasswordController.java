package com.na7ki.backend.auth.forgot_password;

import com.na7ki.backend.auth.forgot_password.dto.response.ForgotPasswordResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.ResendCodeResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.ResetPasswordResponse;
import com.na7ki.backend.auth.forgot_password.dto.response.VerifyCodeResponse;
import com.na7ki.backend.auth.forgot_password.dto.request.ForgotPasswordRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.ResendCodeRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.ResetPasswordRequest;
import com.na7ki.backend.auth.forgot_password.dto.request.VerifyCodeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;





    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> requestResetPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(forgotPasswordService.requestResetPassword(request));
    }

    @PostMapping("/resend-code")
    public ResponseEntity<ResendCodeResponse> resendCode(@Valid @RequestBody ResendCodeRequest request) {
        ResendCodeResponse response = forgotPasswordService.resendCode(request);
        if (response.isResent())
        {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }
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

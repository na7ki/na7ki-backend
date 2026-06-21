package com.na7ki.backend.auth.forgot_password.dto.response;

import com.na7ki.backend.domain.user.verification_code.auxiliary.VerifyCodeStatus;

public record VerifyCodeResponse(
        String providedEmail,
        VerifyCodeStatus status,
        String message
) {
}

package com.na7ki.backend.account_management.reset_password.dto.response;

import com.na7ki.backend.domain.user.verification_code.auxiliary.VerifyCodeStatus;

public record VerifyCodeResponse(

        VerifyCodeStatus status,
        String message

) {
}

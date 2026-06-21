package com.na7ki.backend.domain.user.verification_code.dto;

import com.na7ki.backend.domain.user.verification_code.auxiliary.VerifyCodeStatus;

public record VerifyCodeResult(
        VerifyCodeStatus status,
        String message
) {
}

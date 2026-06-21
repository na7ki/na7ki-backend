package com.na7ki.backend.domain.user.verification_code.model;

import com.na7ki.backend.domain.user.verification_code.enums.VerifyCodeStatus;

public record VerifyCodeResult(
        VerifyCodeStatus status,
        String message
) {
}

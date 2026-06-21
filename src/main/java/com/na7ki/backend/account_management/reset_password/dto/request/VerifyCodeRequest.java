package com.na7ki.backend.account_management.reset_password.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VerifyCodeRequest(

        @NotBlank(message = "A code to verify is required")
        String code

) {
}

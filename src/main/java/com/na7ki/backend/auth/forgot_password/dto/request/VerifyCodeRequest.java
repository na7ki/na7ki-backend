package com.na7ki.backend.auth.forgot_password.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyCodeRequest extends BasePasswordRequest {

        @NotBlank(message = "A code to verify is required")
        private String code;

}
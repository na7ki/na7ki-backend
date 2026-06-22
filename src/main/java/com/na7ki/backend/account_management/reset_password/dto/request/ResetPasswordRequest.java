package com.na7ki.backend.account_management.reset_password.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(

        @NotBlank(message = "Password is required")
        @Size(min = 5, max = 12, message = "password must be between 5 and 12 characters")
        @Pattern(regexp = "^\\s*(?=.*[a-zA-Z])(?=.*\\d)\\S+\\s*$", message = "password must contain at least one alphabetical character, at least one number, and no white spaces at the middle")
        String newPassword,

        @NotBlank(message = "A code to verify is required")
        String code

) {
}

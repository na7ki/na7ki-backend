package com.na7ki.backend.auth.forgot_password.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BasePasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    protected String email;

}

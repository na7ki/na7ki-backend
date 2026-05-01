package com.na7ki.backend.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

    @NotBlank(message = "Write the email associated with your account")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Write the password")
    String password

) {
}

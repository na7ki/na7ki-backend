package com.na7ki.backend.auth.dto.request.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public record LoginRequest(

    @NotBlank(message = "Write the email associated with your account")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Write the password")
    String password

) {
}

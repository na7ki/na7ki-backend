package com.na7ki.backend.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.na7ki.backend.domain.user.entity.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SpecialistRegisterRequest (

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "name must be at least 3 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 12, message = "password must be between 5 and 12 characters")
    @Pattern(regexp = "^\\s*(?=.*[a-zA-Z])(?=.*\\d)\\S+\\s*$", message = "password must contain at least one alphabetical character, at least one number, and no white spaces at the middle")
    String password,

    @NotNull(message = "Gender is required")
    Gender gender,

    @NotBlank (message = "phone number is required")
    @Pattern(regexp = "^\\s*(\\+2)?01\\d{9}\\s*$", message = "Invalid phone number format")
    String phoneNumber,

    @JsonFormat(pattern = "yyyy-M-d")
    @NotNull (message = "date of birth is required")
    LocalDate dateOfBirth,

    @NotBlank (message = "address is required")
    String address

) {}

package com.na7ki.backend.auth.dto.request;

import com.na7ki.backend.auth.entity.auxililary.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public record SpecialistRegisterRequest (

    @NotBlank(message = "Name is required")
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

    LocalDate dateOfBirth,

    @NotBlank (message = "address is required")
    String address,

    @NotBlank(message = "A display image must be chosen")
    String displayImage_path

) {}

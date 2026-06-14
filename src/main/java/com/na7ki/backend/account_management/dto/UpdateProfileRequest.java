package com.na7ki.backend.account_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.na7ki.backend.domain.user.entity.auxililary.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateProfileRequest (

    String name,

    @Email(message = "Invalid email format")
    String email,

    @Pattern(regexp = "^\\s*(\\+2)?01\\d{9}\\s*$", message = "Invalid phone number format")
    String phoneNumber,


    String address,

    Gender gender,

    @JsonFormat(pattern = "yyyy-M-d")
    LocalDate dateOfBirth,

    String displayImage_path

) {}

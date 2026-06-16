package com.na7ki.backend.account_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.na7ki.backend.domain.user.entity.auxililary.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class UpdateProfileRequest {

    private Optional<String> name = Optional.empty();

    private Optional<
            @Email(message = "Invalid email format")
            String> email = Optional.empty();


    private Optional<
            @Pattern(regexp = "^\\s*(\\+2)?01\\d{9}\\s*$", message = "Invalid phone number format")
            String> phoneNumber = Optional.empty();

    private Optional<Gender> gender = Optional.empty();

    private Optional<String> displayImage_path = Optional.empty();



    private Optional<String> address = Optional.empty();

    @JsonFormat(pattern = "yyyy-M-d")
    private Optional<LocalDate> dateOfBirth = Optional.empty();

}

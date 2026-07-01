package com.na7ki.backend.account_management.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.na7ki.backend.domain.user.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class UpdateUserProfileRequest {

    protected Optional<
            @Size(min = 3, message = "name must be at least 3 characters")
            String> name = Optional.empty();

    protected Optional<
            @Email(message = "Invalid email format")
            String> email = Optional.empty();


    protected Optional<
            @Pattern(regexp = "^\\s*(\\+2)?01\\d{9}\\s*$", message = "Invalid phone number format")
            String> phoneNumber = Optional.empty();

    protected Optional<Gender> gender = Optional.empty();

    protected Optional<String> displayImage_path = Optional.empty();

}

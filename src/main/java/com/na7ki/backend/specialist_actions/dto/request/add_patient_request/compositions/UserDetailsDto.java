package com.na7ki.backend.specialist_actions.dto.request.add_patient_request.compositions;

import com.na7ki.backend.domain.user.entity.enums.Gender;
import jakarta.validation.constraints.*;

public record UserDetailsDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotBlank (message = "phone number is required")
        @Pattern(regexp = "^\\s*(\\+2)?01\\d{9}\\s*$", message = "Invalid phone number format")
        String phoneNumber,

        @NotNull (message = "age is required")
        @Min(value = 0, message = "age must be between 0 and 20")
        @Max(value = 20, message = "age must be between 0 and 20")
        @Digits(integer = 2, fraction = 0, message = "age should not contain fractions")
        Short age

) {
}

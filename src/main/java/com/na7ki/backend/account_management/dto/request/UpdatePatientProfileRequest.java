package com.na7ki.backend.account_management.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Optional;

@Data
public final class UpdatePatientProfileRequest extends UpdateUserProfileRequest {

    private Optional<
            @Min(value = 0, message = "age must be between 0 and 20")
            @Max(value = 20, message = "age must be between 0 and 20")
            @Digits(integer = 2, fraction = 0, message = "age should not contain fractions")
            Byte
            > age = Optional.empty();

}

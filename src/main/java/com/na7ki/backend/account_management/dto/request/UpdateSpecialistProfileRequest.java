package com.na7ki.backend.account_management.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public final class UpdateSpecialistProfileRequest extends UpdateUserProfileRequest {

    private Optional<String> address = Optional.empty();

    @JsonFormat(pattern = "yyyy-M-d")
    private Optional<LocalDate> dateOfBirth = Optional.empty();

}

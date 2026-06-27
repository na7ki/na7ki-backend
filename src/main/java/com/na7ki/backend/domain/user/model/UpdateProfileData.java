package com.na7ki.backend.domain.user.model;

import com.na7ki.backend.domain.user.entity.enums.Gender;

import java.time.LocalDate;
import java.util.Optional;

public record UpdateProfileData(

        Optional<String> name,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<Gender> gender,
        Optional<String> displayImage_path,

        // specialist fields
        Optional<String> address,
        Optional<LocalDate> dateOfBirth

) {
}

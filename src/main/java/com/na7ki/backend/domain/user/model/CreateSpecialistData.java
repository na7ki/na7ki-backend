package com.na7ki.backend.domain.user.model;

import com.na7ki.backend.domain.user.entity.enums.Gender;

import java.time.LocalDate;

public record CreateSpecialistData(

        String name,

        String email,

        String password,

        Gender gender,

        String phoneNumber,

        LocalDate dateOfBirth,

        String address

) {
}

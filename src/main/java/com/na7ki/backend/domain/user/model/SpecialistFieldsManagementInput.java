package com.na7ki.backend.domain.user.model;

import java.time.LocalDate;

public record SpecialistFieldsManagementInput(

        String password,
        LocalDate dateOfBirth

) {
}

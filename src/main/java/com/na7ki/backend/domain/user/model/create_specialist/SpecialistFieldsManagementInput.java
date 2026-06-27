package com.na7ki.backend.domain.user.model.create_specialist;

import java.time.LocalDate;

public record SpecialistFieldsManagementInput(

        String password,
        LocalDate dateOfBirth

) {
}

package com.na7ki.backend.domain.user.model.updatable_profile_data;

import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class UpdatableSpecialistData extends UpdatableUserData {

    private Optional<String> address;
    private Optional<LocalDate> dateOfBirth;

}

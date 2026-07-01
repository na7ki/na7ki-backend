package com.na7ki.backend.domain.user.model.updatable_profile_data;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdatablePatientData extends UpdatableUserData {

    private Optional<Byte> age;

}

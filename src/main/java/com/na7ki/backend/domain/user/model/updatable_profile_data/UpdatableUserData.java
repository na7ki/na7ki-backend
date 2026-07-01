package com.na7ki.backend.domain.user.model.updatable_profile_data;

import com.na7ki.backend.domain.user.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class UpdatableUserData {

        protected Optional<String> name;
        protected Optional<String> email;
        protected Optional<String> phoneNumber;
        protected Optional<Gender> gender;
        protected Optional<String> displayImage_path;

}

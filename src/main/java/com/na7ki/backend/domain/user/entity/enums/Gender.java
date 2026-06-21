package com.na7ki.backend.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE, FEMALE;

    @JsonCreator
    public static Gender fromString(String value) {
        return Gender.valueOf(value.trim().toUpperCase());
    }
}

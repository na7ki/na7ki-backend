package com.na7ki.backend.specialist_actions.dto.response.get_patient;

import com.na7ki.backend.domain.user.entity.enums.Gender;

public record UserData(

        String name,
        Gender gender,
        Byte age

) {
}

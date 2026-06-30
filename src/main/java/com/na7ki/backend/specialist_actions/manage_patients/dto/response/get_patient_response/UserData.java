package com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response;

import com.na7ki.backend.domain.user.entity.enums.Gender;

public record UserData(

        String name,
        Gender gender,
        Byte age

) {
}

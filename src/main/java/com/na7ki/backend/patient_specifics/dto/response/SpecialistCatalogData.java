package com.na7ki.backend.patient_specifics.dto.response;

import com.na7ki.backend.domain.user.entity.enums.Gender;

public record SpecialistCatalogData(

        //personal info
        String name,
        Gender gender,
        Byte age,
        String displayImage_path,

        //contact info
        String phoneNumber,
        String address,

        //entity reference
        String specialistId

) {
}

package com.na7ki.backend.auth.util;

import com.na7ki.backend.auth.dto.request.register.PatientRegisterRequest;
import com.na7ki.backend.auth.dto.request.register.SpecialistRegisterRequest;
import com.na7ki.backend.auth.entity.Patient;
import com.na7ki.backend.auth.entity.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    Specialist toSpecialist (SpecialistRegisterRequest Request);

    @Mapping(target = "password", ignore = true)
    Patient toPatient (PatientRegisterRequest Request);

}

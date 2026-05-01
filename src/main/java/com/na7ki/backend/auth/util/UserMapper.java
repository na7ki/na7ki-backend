package com.na7ki.backend.auth.util;

import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.auth.entity.Patient;
import com.na7ki.backend.auth.entity.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "display-image_path", ignore = true)
    @Mapping(target = "specialist_id", ignore = true)
    @Mapping(target = "specialist-personal_images", ignore = true)
    Specialist toSpecialist (SpecialistRegisterRequest Request);

}

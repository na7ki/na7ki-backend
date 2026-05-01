package com.na7ki.backend.domain.user;

import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.domain.user.entity.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "displayImage_path", ignore = true)
    @Mapping(target = "specialistID", ignore = true)
    @Mapping(target = "personalImages_paths", ignore = true)
    Specialist toSpecialist (SpecialistRegisterRequest Request);

}

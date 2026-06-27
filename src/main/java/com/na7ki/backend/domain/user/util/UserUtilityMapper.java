package com.na7ki.backend.domain.user.util;

import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.model.CreateSpecialistData;
import com.na7ki.backend.domain.user.model.SpecialistFieldsManagementInput;
import com.na7ki.backend.domain.user.model.UniqueFields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserUtilityMapper {

    UniqueFields toUniqueFields(CreateSpecialistData data);

    SpecialistFieldsManagementInput toSpecialistFieldsManagementInput(CreateSpecialistData data);

    @Mapping(target = "specialistID", ignore = true)
    @Mapping(target = "personalImages_paths", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "displayImage_path", ignore = true)
    Specialist toSpecialist(CreateSpecialistData data);

}
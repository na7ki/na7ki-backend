package com.na7ki.backend.auth.util;

import com.na7ki.backend.auth.dto.request.SpecialistRegisterRequest;
import com.na7ki.backend.domain.user.model.CreateSpecialistData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialistRegisterMapper {

    CreateSpecialistData toCreateSpecialistData(SpecialistRegisterRequest request);

}

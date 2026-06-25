package com.na7ki.backend.account_management.util;

import com.na7ki.backend.account_management.dto.response.GetSpecialistProfileResponse;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @SubclassMapping(source = Specialist.class, target = GetSpecialistProfileResponse.class)
    GetUserProfileResponse toResponse(User user);

    GetSpecialistProfileResponse toResponse(Specialist specialist);

}

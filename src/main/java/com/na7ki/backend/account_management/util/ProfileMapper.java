package com.na7ki.backend.account_management.util;

import com.na7ki.backend.account_management.dto.request.UpdateProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetSpecialistProfileResponse;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.model.UpdateProfileData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SubclassMapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @SubclassMapping(source = Specialist.class, target = GetSpecialistProfileResponse.class)
    GetUserProfileResponse toResponse(User user);

    GetSpecialistProfileResponse toResponse(Specialist specialist);



    @Mapping(target = "displayImage_path", source = "displayImage_path", qualifiedByName = "emptyToNull")
    UpdateProfileData toUpdateProfileData(UpdateProfileRequest request);

    @Named("emptyToNull")
    default Optional<String> emptyToNull(Optional<String> value) {
        return value.map(v -> v.isBlank() ? null : v);
    }
}

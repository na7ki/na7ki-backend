package com.na7ki.backend.account_management.mapper;

import com.na7ki.backend.account_management.dto.request.UpdatePatientProfileRequest;
import com.na7ki.backend.account_management.dto.request.UpdateSpecialistProfileRequest;
import com.na7ki.backend.account_management.dto.request.UpdateUserProfileRequest;
import com.na7ki.backend.account_management.dto.response.GetPatientProfileResponse;
import com.na7ki.backend.account_management.dto.response.GetSpecialistProfileResponse;
import com.na7ki.backend.account_management.dto.response.GetUserProfileResponse;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.model.updatable_profile_data.UpdatablePatientData;
import com.na7ki.backend.domain.user.model.updatable_profile_data.UpdatableSpecialistData;
import com.na7ki.backend.domain.user.model.updatable_profile_data.UpdatableUserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SubclassMapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @SubclassMapping(source = Specialist.class, target = GetSpecialistProfileResponse.class)
    @SubclassMapping(source = Patient.class, target = GetPatientProfileResponse.class)
    GetUserProfileResponse toResponse(User user);

    GetSpecialistProfileResponse toResponse(Specialist specialist);

    GetPatientProfileResponse toResponse(Patient patient);





    @Mapping(target = "displayImage_path", source = "displayImage_path", qualifiedByName = "emptyToNull")
    @SubclassMapping(source = UpdateSpecialistProfileRequest.class, target = UpdatableSpecialistData.class)
    @SubclassMapping(source = UpdatePatientProfileRequest.class, target = UpdatablePatientData.class)
    UpdatableUserData toUpdateProfileData(UpdateUserProfileRequest request);

    UpdatableSpecialistData toUpdateProfileData(UpdateSpecialistProfileRequest request);

    UpdatablePatientData toUpdateProfileData(UpdatePatientProfileRequest request);





    @Named("emptyToNull")
    default Optional<String> emptyToNull(Optional<String> value) {
        return value.map(v -> v.isBlank() ? null : v);
    }
}

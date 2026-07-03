package com.na7ki.backend.domain.user.mapper;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.domain.user.model.create_specialist.CreateSpecialistData;
import com.na7ki.backend.domain.user.model.create_specialist.SpecialistFieldsManagementInput;
import com.na7ki.backend.domain.user.model.UniqueFields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //ignored because they are handled by the server
    @Mapping(target = "specialistId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "displayImage_path", ignore = true)
    @Mapping(target = "personalImages_paths", ignore = true)
    Specialist toSpecialist(CreateSpecialistData data);

    SpecialistFieldsManagementInput toSpecialistFieldsManagementInput(CreateSpecialistData data);

    UniqueFields toUniqueFields(CreateSpecialistData data);



    //ignored because they are handled by the server
    @Mapping(target = "patientID", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "displayImage_path", ignore = true)

    @Mapping(target = "name",        source = "userDetailsData.name")
    @Mapping(target = "email",       source = "userDetailsData.email")
    @Mapping(target = "gender",      source = "userDetailsData.gender")
    @Mapping(target = "phoneNumber", source = "userDetailsData.phoneNumber")
    @Mapping(target = "age",         source = "userDetailsData.age")
    Patient toPatient(CreatePatientData data);

    @Mapping(target = "email",       source = "userDetailsData.email")
    @Mapping(target = "phoneNumber", source = "userDetailsData.phoneNumber")
    UniqueFields toUniqueFields(CreatePatientData data);

}
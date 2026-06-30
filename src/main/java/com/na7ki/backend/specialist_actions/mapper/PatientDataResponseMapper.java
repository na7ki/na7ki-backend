package com.na7ki.backend.specialist_actions.mapper;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.specialist_actions.dto.response.get_patient.MedicalData;
import com.na7ki.backend.specialist_actions.dto.response.get_patient.PatientDataResponse;
import com.na7ki.backend.specialist_actions.dto.response.get_patient.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientDataResponseMapper {

    @Mapping(target = "userData", source = "patient")
    @Mapping(target = "medicalData", source = "medicalDetails")
    PatientDataResponse toResponse(Patient patient);

    UserData toUserData(Patient patient);

    @Mapping(target = "caseInfoData", source = "additionalInfoData.caseInfoData")
    MedicalData toMedicalData(com.na7ki.backend.domain.user.entity.patient_medical_details.PatientMedicalDetails medicalDetails);
}
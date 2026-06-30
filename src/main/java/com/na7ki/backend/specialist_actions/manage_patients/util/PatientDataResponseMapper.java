package com.na7ki.backend.specialist_actions.manage_patients.util;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response.MedicalData;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response.PatientDataResponse;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response.UserData;
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
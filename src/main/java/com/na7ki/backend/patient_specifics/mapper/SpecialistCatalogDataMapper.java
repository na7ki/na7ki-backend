package com.na7ki.backend.patient_specifics.mapper;

import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.patient_specifics.dto.response.SpecialistCatalogData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialistCatalogDataMapper {

    List<SpecialistCatalogData> toSpecialistCatalogData(List<Specialist> specialist);

}

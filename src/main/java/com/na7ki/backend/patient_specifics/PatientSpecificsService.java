package com.na7ki.backend.patient_specifics;

import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.patient_specifics.dto.response.SpecialistCatalogData;
import com.na7ki.backend.patient_specifics.mapper.SpecialistCatalogDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientSpecificsService {

    private final UserService userService;

    private final SpecialistCatalogDataMapper mapper;





    public List<SpecialistCatalogData> getAllSpecialists () {
        return mapper.toSpecialistCatalogData(userService.getAllSpecialists());
    }

}

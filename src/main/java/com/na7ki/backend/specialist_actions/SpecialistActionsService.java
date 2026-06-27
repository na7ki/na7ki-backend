package com.na7ki.backend.specialist_actions;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.specialist_actions.dto.response.AddPatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialistActionsService {

    private final UserService userService;





    public AddPatientResponse addPatient (CreatePatientData request) {
        Patient createdPatient = userService.createPatient(request);
        return new AddPatientResponse(createdPatient.getId(), createdPatient.getPatientID() , createdPatient.getPassword());
    }

}

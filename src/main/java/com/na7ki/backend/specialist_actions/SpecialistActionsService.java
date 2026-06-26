package com.na7ki.backend.specialist_actions;

import com.na7ki.backend.specialist_actions.dto.request.add_patient_request.AddPatientRequest;
import com.na7ki.backend.specialist_actions.dto.response.AddPatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialistActionsService {

    public AddPatientResponse addPatient (AddPatientRequest request) {
        return new AddPatientResponse("", "");
    }

}

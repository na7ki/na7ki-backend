package com.na7ki.backend.specialist_actions;

import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.specialist_actions.dto.response.AddPatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/add-patient")
@RequiredArgsConstructor
public class SpecialistActionsController {

    SpecialistActionsService specialistActionsService;





    @PostMapping
    public ResponseEntity<AddPatientResponse> addPatient (CreatePatientData request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specialistActionsService.addPatient(request));
    }

}

package com.na7ki.backend.specialist_actions;

import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.specialist_actions.dto.response.AddPatientResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specialist")
@RequiredArgsConstructor
public class SpecialistActionsController {

    private final SpecialistActionsService specialistActionsService;





    @PostMapping("/add-patient")
    public ResponseEntity<AddPatientResponse> addPatient (
            @RequestBody @Valid CreatePatientData request,
            @AuthenticationPrincipal User specialist
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                specialistActionsService.addPatient(request, ((Specialist) specialist)));
    }

}

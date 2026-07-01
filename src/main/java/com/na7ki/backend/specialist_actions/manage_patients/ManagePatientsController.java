package com.na7ki.backend.specialist_actions.manage_patients;

import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.model.PatientSummaryData;
import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.AddPatientResponse;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response.PatientDataResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialist/patients")
@RequiredArgsConstructor
public class ManagePatientsController {

    private final ManagePatientsService managePatientsService;





    @PostMapping()
    public ResponseEntity<AddPatientResponse> addPatient (
            @RequestBody @Valid CreatePatientData request,
            @AuthenticationPrincipal User specialist
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                managePatientsService.addPatient(request, ((Specialist) specialist)));
    }

    @GetMapping()
    public ResponseEntity<List<PatientSummaryData>> getPatients (@AuthenticationPrincipal User specialist) {
        return ResponseEntity.status(HttpStatus.OK).body(managePatientsService.getPatients(((Specialist) specialist)));
    }

    @GetMapping("/{patientSpecificId}")
    public ResponseEntity<PatientDataResponse> getPatient (
            @PathVariable
            @Pattern(regexp = "^PT\\d+$", message = "Invalid Patient ID format. Must be PT followed by one or more digits")
            String patientSpecificId,

            @AuthenticationPrincipal User specialist
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(managePatientsService.getPatient(patientSpecificId, ((Specialist) specialist).getUserId()));
    }

}

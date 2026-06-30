package com.na7ki.backend.specialist_actions.manage_patients;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.core.email.model.PatientPasswordEmail;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.model.PatientSummaryData;
import com.na7ki.backend.domain.user.model.create_patient.CreatePatientData;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.AddPatientResponse;
import com.na7ki.backend.specialist_actions.manage_patients.dto.response.get_patient_response.PatientDataResponse;
import com.na7ki.backend.specialist_actions.manage_patients.exception.SpecialistRequestingNonAssociatedPatientDataException;
import com.na7ki.backend.specialist_actions.manage_patients.util.PatientDataResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagePatientsService {

    private final UserService userService;
    private final EmailService emailService;

    private final PatientDataResponseMapper mapper;





    public AddPatientResponse addPatient (CreatePatientData request, Specialist associatedSpecialist) {
        Pair<Patient, String> patient_and_password = userService.createPatient(request, associatedSpecialist);
        Patient createdPatient = patient_and_password.getFirst();
        String generatedRawPassword = patient_and_password.getSecond();

        emailService.sendAddedPatientPassword(
                createdPatient.getEmail(),
                new PatientPasswordEmail(
                        createdPatient.getName(),
                        associatedSpecialist.getName(),
                        generatedRawPassword
                )
        );

        return new AddPatientResponse(createdPatient.getUserId(), createdPatient.getPatientID());
    }

    public List<PatientSummaryData> getPatients (Specialist specialist) {
        return userService.getAssociatedPatients(specialist);
    }

    public PatientDataResponse getPatient (String patientId, Long supervisorUserId) {
        Patient retreivedPatient = userService.findByPatientId(patientId);

        if (!retreivedPatient.getSupervisor().getUserId().equals(supervisorUserId)) {
            throw new SpecialistRequestingNonAssociatedPatientDataException("A specialist is requesting the data of a patient that they aren't their supervisor");
        }

        return mapper.toResponse(retreivedPatient);
    }

}

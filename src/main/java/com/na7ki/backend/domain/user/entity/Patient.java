package com.na7ki.backend.domain.user.entity;

import com.na7ki.backend.domain.user.entity.patient_medical_details.PatientMedicalDetails;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("PATIENT")
@Data
public class Patient extends User {

    @Column(name="patient_id", nullable = false, unique=true, length=15)
    private String patientID;

    @Embedded
    private PatientMedicalDetails medicalDetails;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Specialist supervisor;





    @Override
    public void anonymize(Long deletionUserId) {
        super.anonymize(deletionUserId);

        //change specific id
        this.setPatientID(this.getPatientID() + "_DL");
    }

}

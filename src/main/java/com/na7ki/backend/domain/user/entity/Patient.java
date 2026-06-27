package com.na7ki.backend.domain.user.entity;

import com.na7ki.backend.domain.user.entity.patient_medical_details.PatientMedicalDetails;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("PATIENT")
@Data
public class Patient extends User {

    @Column(name="patient_id", nullable = false, unique=true, updatable=false, length=15)
    private String patientID;

    @Embedded
    private PatientMedicalDetails medicalDetails;

}

package com.na7ki.backend.domain.user.entity;

import com.na7ki.backend.domain.user.entity.patientdetails.PatientMedicalDetails;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "patient")
@DiscriminatorValue("PATIENT")
@Data
public class Patient extends User {

    @Column(name="patient_id", nullable = false, unique=true, updatable=false, length=15)
    private String patientID;

    @Embedded
    private PatientMedicalDetails details;

}

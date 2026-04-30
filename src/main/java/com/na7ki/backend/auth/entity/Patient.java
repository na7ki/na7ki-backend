package com.na7ki.backend.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
@DiscriminatorValue("PATIENT")
public class Patient extends User {

    @Column(name="patient_id", nullable = false, unique=true, updatable=false, length=15)
    private String patientID;

    @Column(name = "medical_history", nullable = false, length = 2000)
    private String medicalHistory;

}

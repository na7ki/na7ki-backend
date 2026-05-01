package com.na7ki.backend.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "patient")
@DiscriminatorValue("PATIENT")
@Data
public class Patient extends User {

//    private static long counter = 0;
//
//    @Column(name="patient_id", nullable = false, unique=true, updatable=false, length=15)
//    private String patientID;

    @Column(name = "medical_history", nullable = false, length = 2000)
    private String medicalHistory;

}

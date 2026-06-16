package com.na7ki.backend.domain.user.repository;

import com.na7ki.backend.domain.user.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}

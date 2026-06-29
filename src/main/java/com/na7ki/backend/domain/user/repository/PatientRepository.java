package com.na7ki.backend.domain.user.repository;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.model.PatientSummaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("""
    SELECT new com.na7ki.backend.domain.user.model.PatientSummaryData(p.name, p.age)
    FROM Patient p
    WHERE p.supervisor = :specialist
    """)
    List<PatientSummaryData> findPatientSummariesOfSpecialist(@Param("specialist") Specialist specialist);

}

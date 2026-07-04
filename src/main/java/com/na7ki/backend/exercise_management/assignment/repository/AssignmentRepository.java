package com.na7ki.backend.exercise_management.assignment.repository;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.exercise_management.assignment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByPatient(Patient patient);

}

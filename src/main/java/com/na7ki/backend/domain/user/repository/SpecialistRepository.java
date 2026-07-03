package com.na7ki.backend.domain.user.repository;

import com.na7ki.backend.domain.user.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    Optional<Specialist> findBySpecialistId(String specialistId);

}

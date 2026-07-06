package com.na7ki.backend.domain.exercise.pronunciation.Repository;

import com.na7ki.backend.domain.exercise.pronunciation.entity.PronunciationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PronunciationAttemptRepository extends JpaRepository<PronunciationAttempt, Long> {

    List<PronunciationAttempt> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}

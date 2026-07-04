package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.entity.TaskResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {

    List<TaskResult> findByPatientIdOrderByStartedAtDesc(Long patientId);

    Optional<TaskResult> findByPatientIdAndTaskIdAndStartedAt(
            Long patientId, Integer taskId, OffsetDateTime startedAt);
}
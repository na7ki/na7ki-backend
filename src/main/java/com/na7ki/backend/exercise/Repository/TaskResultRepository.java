package com.na7ki.backend.exercise.Repository;

import com.na7ki.backend.exercise.Entity.TaskResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {

    List<TaskResult> findByCaseEntity_IdOrderByStartedAtDesc(Long caseId);

    // Backs the idempotency check from §2.1 before relying solely on the DB
    // unique constraint — lets us return the existing row instead of a 500
    // when the app resubmits on reconnect.
    Optional<TaskResult> findByCaseEntity_IdAndTaskIdAndStartedAt(
            Long caseId, Integer taskId, OffsetDateTime startedAt);
}
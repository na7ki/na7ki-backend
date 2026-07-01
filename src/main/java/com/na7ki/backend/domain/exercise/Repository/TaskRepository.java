package com.na7ki.backend.domain.exercise.Repository;

import com.na7ki.backend.domain.exercise.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
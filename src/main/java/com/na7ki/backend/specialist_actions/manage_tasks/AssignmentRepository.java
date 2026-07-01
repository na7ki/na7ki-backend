package com.na7ki.backend.specialist_actions.manage_tasks;

import com.na7ki.backend.specialist_actions.manage_tasks.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}

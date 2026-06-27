package com.na7ki.backend.exercise.Repository;

import com.na7ki.backend.exercise.Entity.ExerciseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseItemRepository extends JpaRepository<ExerciseItem, Long> {
    
    // Finds all items for a specific task key and sorts them by orderIndex
    List<ExerciseItem> findByTask_TaskKeyOrderByOrderIndexAsc(String taskKey);

    // Finds all items for a specific task id and sorts them by orderIndex
    List<ExerciseItem> findByTask_IdOrderByOrderIndexAsc(Long taskId);
}
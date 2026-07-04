package com.na7ki.backend.exercise_management.assignment.repository;

import com.na7ki.backend.exercise_management.assignment.entity.AssignedExercise;
import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AssignedExerciseRepository extends JpaRepository<AssignedExercise, Long> {

    @Modifying
    @Query("UPDATE AssignedExercise e " +
            "SET e.isSolved = true, e.solutionTimestamp = :timestamp " +
            "WHERE e.id = :exerciseId " +
            "AND e.type = :exerciseType " +
            "AND e.assignment.patient.userId = :patientId")
    int markAsSolved(@Param("exerciseId") Long exerciseId,
                     @Param("exerciseType") ExerciseType exerciseType,
                     @Param("patientId") Long patientId,
                     @Param("timestamp") Date timestamp);
}

package com.na7ki.backend.exercise_management.assignment;

import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.exercise_management.assignment.entity.Assignment;
import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import com.na7ki.backend.exercise_management.assignment.exception.ExerciseNotFound;
import com.na7ki.backend.exercise_management.assignment.repository.AssignedExerciseRepository;
import com.na7ki.backend.exercise_management.assignment.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignedExerciseRepository assignedExerciseRepository;





    public List<Assignment> getAssignmentsByPatient(Patient patient) {
        return assignmentRepository.findByPatient(patient);
    }

    @Transactional
    public void markAssignedExerciseSolved(Long patientId, ExerciseType exerciseType, Long exerciseId) {
        int rowsUpdated = assignedExerciseRepository.markAsSolved(
                exerciseId,
                exerciseType,
                patientId,
                new Date()
        );

        if (rowsUpdated == 0) {
            throw new ExerciseNotFound("Assigned exercise not found for this patient");
        }
    }

}

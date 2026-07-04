package com.na7ki.backend.exercise_management.dto.response.patient_assigned_exercises_list_response;

import com.na7ki.backend.exercise_management.dto.response.AssignmentPackage;
import lombok.Data;

import java.util.List;

@Data
public class AssignedPackage extends AssignmentPackage {

    private List<AssignedExerciseDto> assignedExercises;

}

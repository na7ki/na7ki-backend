package com.na7ki.backend.exercise_management.dto.response.patient_assigned_exercises_list_response;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AssignedExerciseDto {

    protected Boolean isSolved;
    protected Date solutionTimestamp;

}

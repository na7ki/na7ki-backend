package com.na7ki.backend.exercise_management.dto.response.specialist_exercises_list_response;

import com.na7ki.backend.exercise_management.dto.response.AssignmentPackage;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentPackageOfQuestions extends AssignmentPackage {

        private List<AssignmentQuestion> assignmentQuestions;

}

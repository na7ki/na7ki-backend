package com.na7ki.backend.task_management.dto.response.exercises_list_response;

import lombok.Data;

import java.util.List;

@Data
public class AssignmentPackageOfQuestions extends AssignmentPackage {

        private List<AssignmentQuestion> assignmentQuestions;

}

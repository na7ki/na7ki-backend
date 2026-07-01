package com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_response;

import lombok.Data;

import java.util.List;

@Data
public class PackageOfQuestions extends Package {

        private List<Question> questions;

}

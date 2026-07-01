package com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request;

import lombok.Data;

import java.util.List;

@Data
public class PackageOfTasks extends Package {

    private List<Task> tasks;

}

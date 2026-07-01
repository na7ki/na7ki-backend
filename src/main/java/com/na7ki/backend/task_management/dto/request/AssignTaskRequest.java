package com.na7ki.backend.task_management.dto.request;

import jakarta.validation.constraints.Positive;

import java.util.List;

public record AssignTaskRequest(

        List<
                @Positive(message = "invalid question id. Must be a positive whole number")
                Long>
        assignedQuestionsIds,

        List<
                @Positive(message = "invalid task id. Must be a positive whole number")
                Long>
        assignedTasksIds

) {
}

package com.na7ki.backend.exercise_management.mapper;

import com.na7ki.backend.domain.exercise.dto.PackageDTO;
import com.na7ki.backend.domain.exercise.dto.QuestionDTO;
import com.na7ki.backend.domain.exercise.dto.TaskResponse;
import com.na7ki.backend.domain.exercise.entity.Packages;
import com.na7ki.backend.exercise_management.assignment.entity.AssignedExercise;
import com.na7ki.backend.exercise_management.dto.response.AssignmentPackage;
import com.na7ki.backend.exercise_management.dto.response.patient_assigned_exercises_list_response.AssignedQuestion;
import com.na7ki.backend.exercise_management.dto.response.patient_assigned_exercises_list_response.AssignedTask;
import com.na7ki.backend.exercise_management.dto.response.specialist_exercises_list_response.AssignmentQuestion;
import com.na7ki.backend.exercise_management.dto.response.specialist_exercises_list_response.AssignmentTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExercisesDataMapper {

    @Mapping(source = "title", target = "name")
    @Mapping(source = "coverImage.imageUrl", target = "coverImage_url")
    AssignmentPackage toAssignmentPackage(PackageDTO packageDTO);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "coverImage.imageUrl", target = "coverImage_url")
    AssignmentPackage toAssignmentPackage(Packages packages);



    @Mapping(source = "questionText", target = "questionStatement")
    AssignmentQuestion toAssignmentQuestion(QuestionDTO questionDTO);

    List<AssignmentQuestion> toAssignmentQuestionList(List<QuestionDTO> questionDTOs);



    AssignmentTask toAssignmentTask(TaskResponse taskResponse);

    List<AssignmentTask> toAssignmentTaskList(List<TaskResponse> taskResponses);



    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "question.questionText", target = "questionStatement")
    AssignedQuestion toAssignedQuestion (AssignedExercise exercise);

    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "task.taskKey", target = "taskKey")
    @Mapping(source = "task.category", target = "category")
    @Mapping(source = "task.title", target = "title")
    @Mapping(source = "task.description", target = "description")
    AssignedTask toAssignedTask (AssignedExercise exercise);

}

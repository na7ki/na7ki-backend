package com.na7ki.backend.task_management.mapper;

import com.na7ki.backend.domain.exercise.Entity.Packages;
import com.na7ki.backend.domain.exercise.Entity.Task;
import com.na7ki.backend.domain.exercise.dto.PackageDTO;
import com.na7ki.backend.domain.exercise.dto.QuestionDTO;
import com.na7ki.backend.domain.exercise.dto.TaskResponse;
import com.na7ki.backend.domain.exercise.Entity.Question;
import com.na7ki.backend.task_management.dto.response.exercises_list_response.AssignmentPackage;
import com.na7ki.backend.task_management.dto.response.exercises_list_response.AssignmentQuestion;
import com.na7ki.backend.task_management.dto.response.exercises_list_response.AssignmentTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExercisesDataMapper {

    @Mapping(source = "title", target = "name")
    AssignmentPackage toAssignmentPackage(PackageDTO packageDTO);

    List<AssignmentPackage> toAssignmentPackageList(List<PackageDTO> packageDTOs);

    @Mapping(source = "title", target = "name")
    AssignmentPackage toAssignmentPackage(Packages packages);



    @Mapping(source = "questionText", target = "questionStatement")
    AssignmentQuestion toAssignmentQuestion(QuestionDTO questionDTO);

    List<AssignmentQuestion> toAssignmentQuestionList(List<QuestionDTO> questionDTOs);

    @Mapping(source = "questionText", target = "questionStatement")
    AssignmentQuestion toAssignmentQuestion(Question questionDTO);



    AssignmentTask toAssignmentTask(TaskResponse taskResponse);

    List<AssignmentTask> toAssignmentTaskList(List<TaskResponse> taskResponses);

    AssignmentTask toAssignmentTask(Task task);

}

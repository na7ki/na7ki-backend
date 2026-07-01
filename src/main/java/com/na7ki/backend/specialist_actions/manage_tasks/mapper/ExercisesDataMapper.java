package com.na7ki.backend.specialist_actions.manage_tasks.mapper;

import com.na7ki.backend.exercise.dto.PackageDTO;
import com.na7ki.backend.exercise.dto.QuestionDTO;
import com.na7ki.backend.exercise.dto.TaskResponse;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.Package;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.Question;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExercisesDataMapper {

    @Mapping(source = "title", target = "name")
    Package toPackage(PackageDTO packageDTO);

    List<Package> toPackageList(List<PackageDTO> packageDTOs);



    @Mapping(source = "questionText", target = "questionStatement")
    Question toQuestion(QuestionDTO questionDTO);

    List<Question> toQuestionList(List<QuestionDTO> questionDTOs);



    Task toTask(TaskResponse taskResponse);

    List<Task> toTaskList(List<TaskResponse> taskResponses);

}

package com.na7ki.backend.specialist_actions.manage_tasks;

import com.na7ki.backend.exercise.Service.ExerciseService;
import com.na7ki.backend.exercise.Service.TaskService;
import com.na7ki.backend.exercise.dto.PackageDTO;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.Package;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.PackageOfQuestions;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_request.PackageOfTasks;
import com.na7ki.backend.specialist_actions.manage_tasks.mapper.ExercisesDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageTasksService {

    private final ExerciseService exerciseService;
    private final TaskService taskService;

    private final ExercisesDataMapper mapper;





    public List<Package> getAllExercises () {
        return exerciseService.getAllPackages().stream()
                .map(this::toTypedPackage)
                .toList();
    }

    private Package toTypedPackage(PackageDTO packageFromDomain) {
        Package base = mapper.toPackage(packageFromDomain);

        if (base.getId().equals(3L)) {
            PackageOfTasks p = new PackageOfTasks();
            Package.copyBase(base, p);
            p.setTasks(mapper.toTaskList(taskService.getAllTasks()));
            return p;
        } else {
            PackageOfQuestions p = new PackageOfQuestions();
            Package.copyBase(base, p);
            p.setQuestions(mapper.toQuestionList(exerciseService.getQuestionsByPackage(base.getId())));
            return p;
        }
    }

}

package com.na7ki.backend.specialist_actions.manage_tasks;

import com.na7ki.backend.domain.exercise.Service.ExerciseService;
import com.na7ki.backend.domain.exercise.Service.TaskService;
import com.na7ki.backend.domain.exercise.dto.PackageDTO;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.request.AssignTaskRequest;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_response.Package;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_response.PackageOfQuestions;
import com.na7ki.backend.specialist_actions.manage_tasks.dto.response.exercises_list_response.PackageOfTasks;
import com.na7ki.backend.specialist_actions.manage_tasks.entity.AssignedExercise;
import com.na7ki.backend.specialist_actions.manage_tasks.entity.Assignment;
import com.na7ki.backend.specialist_actions.manage_tasks.entity.enums.ExerciseType;
import com.na7ki.backend.specialist_actions.manage_tasks.mapper.ExercisesDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageTasksService {

    private final ExerciseService exerciseService;
    private final TaskService taskService;
    private final UserService userService;

    private final ExercisesDataMapper mapper;

    private final AssignmentRepository assignmentRepository;





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

    public void assignTask (Specialist supervisor, String patientSpecificId, AssignTaskRequest request) {

        Assignment assignment = Assignment.builder()
                .supervisor(supervisor)
                .patient((userService.findByPatientId(patientSpecificId)))
                .build();

        List<AssignedExercise> assignedExercises = new ArrayList<>();

        for(Long questionId : request.assignedQuestionsIds())
        {
            AssignedExercise assignedExercise = AssignedExercise.builder()
                    .type(ExerciseType.QUESTION)
                    .question(exerciseService.getRawQuestionById(questionId))
                    .task(null)
                    .assignment(assignment)
                    .build();

            assignedExercises.add(assignedExercise);
        }

        for(Long taskId : request.assignedTasksIds())
        {
            AssignedExercise assignedExercise = AssignedExercise.builder()
                    .type(ExerciseType.TASK)
                    .question(null)
                    .task(taskService.getRawTaskById(taskId))
                    .assignment(assignment)
                    .build();

            assignedExercises.add(assignedExercise);
        }

        assignment.setAssignedExercises(assignedExercises);

        assignmentRepository.save(assignment);
    }

}

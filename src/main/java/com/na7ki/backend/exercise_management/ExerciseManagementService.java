package com.na7ki.backend.exercise_management;

import com.na7ki.backend.exercise_management.assignment.AssignmentService;
import com.na7ki.backend.exercise_management.dto.response.patient_assigned_exercises_list_response.AssignedPackage;
import com.na7ki.backend.exercise_management.exception.NothingChosenToBeAssignedException;
import com.na7ki.backend.domain.exercise.entity.Packages;
import com.na7ki.backend.domain.exercise.entity.Question;
import com.na7ki.backend.domain.exercise.entity.Task;
import com.na7ki.backend.exercise_management.dto.request.AssignExerciseRequest;
import com.na7ki.backend.notification.NotificationService;

import com.na7ki.backend.domain.exercise.Service.ExerciseService;
import com.na7ki.backend.domain.exercise.Service.TaskService;
import com.na7ki.backend.domain.exercise.dto.PackageDTO;
import com.na7ki.backend.domain.user.entity.Patient;
import com.na7ki.backend.domain.user.entity.Specialist;
import com.na7ki.backend.domain.user.service.UserService;
import com.na7ki.backend.specialist_actions.manage_patients.exception.SpecialistRequestingNonAssociatedPatientDataException;
import com.na7ki.backend.exercise_management.assignment.repository.AssignmentRepository;
import com.na7ki.backend.exercise_management.dto.response.AssignmentPackage;
import com.na7ki.backend.exercise_management.dto.response.specialist_exercises_list_response.AssignmentPackageOfQuestions;
import com.na7ki.backend.exercise_management.dto.response.specialist_exercises_list_response.AssignmentPackageOfTasks;
import com.na7ki.backend.exercise_management.assignment.entity.AssignedExercise;
import com.na7ki.backend.exercise_management.assignment.entity.Assignment;
import com.na7ki.backend.exercise_management.assignment.entity.enums.ExerciseType;
import com.na7ki.backend.exercise_management.mapper.ExercisesDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExerciseManagementService {

    private final ExerciseService exerciseService;
    private final TaskService taskService;
    private final UserService userService;
    private final AssignmentService assignmentService;

    private final ExercisesDataMapper mapper;

    private final AssignmentRepository assignmentRepository;

    private final NotificationService notificationService; 





    public List<AssignmentPackage> getAllExercises () {
        return exerciseService.getAllPackages().stream()
                .map(this::toTypedPackage)
                .toList();
    }

    private AssignmentPackage toTypedPackage(PackageDTO packageDTO) {

        AssignmentPackage base = mapper.toAssignmentPackage(packageDTO);

        if (base.getId().equals(3L)) {
            AssignmentPackageOfTasks p = new AssignmentPackageOfTasks();
            AssignmentPackage.copyBase(base, p);
            p.setAssignmentTasks(mapper.toAssignmentTaskList(taskService.getAllTasks()));
            return p;
        } else {
            AssignmentPackageOfQuestions p = new AssignmentPackageOfQuestions();
            AssignmentPackage.copyBase(base, p);
            p.setAssignmentQuestions(mapper.toAssignmentQuestionList(exerciseService.getQuestionsByPackage(base.getId())));
            return p;
        }
    }

    public void assignExercise(Specialist supervisor, String patientSpecificId, AssignExerciseRequest request) {

        Patient associatedPatient = userService.findByPatientId(patientSpecificId);

        if (!associatedPatient.getSupervisor().getUserId().equals(supervisor.getUserId())) {
            throw new SpecialistRequestingNonAssociatedPatientDataException(
                    "A specialist is assigning tasks to a patient that they aren't their supervisor");
        }

        Assignment assignment = Assignment.builder()
                .supervisor(supervisor)
                .patient(associatedPatient)
                .build();

        List<AssignedExercise> assignedExercises = new ArrayList<>();

        if (request.assignedQuestionsIds() != null) {
            for (Long questionId : request.assignedQuestionsIds()) {
                AssignedExercise assignedExercise = AssignedExercise.builder()
                        .type(ExerciseType.QUESTION)
                        .question(exerciseService.getRawQuestionById(questionId))
                        .task(null)
                        .assignment(assignment)
                        .build();

                assignedExercises.add(assignedExercise);
            }
        }

        if (request.assignedTasksIds() != null) {
            for (Long taskId : request.assignedTasksIds()) {
                AssignedExercise assignedExercise = AssignedExercise.builder()
                        .type(ExerciseType.TASK)
                        .question(null)
                        .task(taskService.getRawTaskById(taskId))
                        .assignment(assignment)
                        .build();

                assignedExercises.add(assignedExercise);
            }
        }

        if (assignedExercises.isEmpty()) {
            throw new NothingChosenToBeAssignedException("At least one task or question must be assigned.");
        }

        assignment.setAssignedExercises(assignedExercises);

        assignmentRepository.save(assignment);

        notifyPatientOfNewAssignment(associatedPatient, supervisor, assignment);
    }

    private void notifyPatientOfNewAssignment(Patient patient, Specialist supervisor, Assignment assignment) {

        List<Map<String, Object>> assignedItems = new ArrayList<>();

        for (AssignedExercise exercise : assignment.getAssignedExercises()) {

            Map<String, Object> item = new HashMap<>();

            if (exercise.getType() == ExerciseType.TASK && exercise.getTask() != null) {

                Task task = exercise.getTask();

                item.put("type", "TASK");
                item.put("id", task.getId());
                item.put("title", task.getTitle());

            } else if (exercise.getType() == ExerciseType.QUESTION && exercise.getQuestion() != null) {

                Question question = exercise.getQuestion();

                item.put("type", "QUESTION");
                item.put("id", question.getId());
                item.put("title", question.getQuestionText());
            }

            assignedItems.add(item);
        }

        Map<String, Object> details = new HashMap<>();
        details.put("assignmentId", assignment.getId());
        details.put("itemCount", assignedItems.size());
        details.put("items", assignedItems);

        notificationService.notifyTaskAssigned(patient, supervisor, assignment.getId(), details);
    }

    public List<AssignedPackage> getExercisesOfPatient (Patient patient) {

        List<Assignment> assignments = assignmentService.getAssignmentsByPatient(patient);

        List<AssignedPackage> assignedPackages = new ArrayList<>();

        for (Assignment assignment : assignments)
        {
            for (AssignedExercise exercise : assignment.getAssignedExercises())
            {
                if (exercise.getType().equals(ExerciseType.QUESTION)) {
                    //get the question
                    Question question = exercise.getQuestion();
                    Packages pkg = question.getPkg();
                    Long pkgId = pkg.getId();

                    // Check if the Package to which the extracted question already exists in the list that will be returned
                    AssignedPackage potentialExistingPkg = assignedPackages.stream()
                            .filter(p -> p.getId().equals(pkgId))
                            .findFirst()
                            .orElse(null);

                    //if the package to which the question belongs already is in the list
                    if (potentialExistingPkg != null) {
                        potentialExistingPkg.getAssignedExercises().add(mapper.toAssignedQuestion(exercise));
                    //if it's not in the list
                    } else {
                        AssignedPackage newPkg = new AssignedPackage();
                        AssignmentPackage.copyBase(mapper.toAssignmentPackage(pkg), newPkg);
                        newPkg.setAssignedExercises(new ArrayList<>(List.of(mapper.toAssignedQuestion(exercise))));
                        assignedPackages.add(newPkg);
                    }

                } else if (exercise.getType().equals(ExerciseType.TASK)) {
                    //get the task
                    Task task = exercise.getTask();
                    Packages pkg = task.getPkg();
                    Long pkgId = pkg.getId();

                    // Check if the Package to which the extracted task already exists in the list that will be returned
                    AssignedPackage potentialExistingPkg = assignedPackages.stream()
                            .filter(p -> p.getId().equals(pkgId))
                            .findFirst()
                            .orElse(null);

                    //if the package to which the question belongs already is in the list
                    if (potentialExistingPkg != null) {
                        potentialExistingPkg.getAssignedExercises().add(mapper.toAssignedTask(exercise));
                    //if it's not in the list
                    } else {
                        AssignedPackage newPkg = new AssignedPackage();
                        AssignmentPackage.copyBase(mapper.toAssignmentPackage(pkg), newPkg);
                        newPkg.setAssignedExercises(new ArrayList<>(List.of(mapper.toAssignedTask(exercise))));
                        assignedPackages.add(newPkg);
                    }
                }
            }
        }

        return assignedPackages;
    }
}

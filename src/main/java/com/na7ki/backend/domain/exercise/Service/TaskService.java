package com.na7ki.backend.domain.exercise.Service;

import com.na7ki.backend.domain.exercise.dto.TaskResponse;
import com.na7ki.backend.domain.exercise.Entity.Task;
import com.na7ki.backend.domain.exercise.Repository.TaskRepository;
import com.na7ki.backend.domain.exercise.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // 1. Retrieve all tasks
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 2. Retrieve a single task by ID
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("AssignmentTask not found with id: " + id));
        
        return mapToResponse(task);
    }

    public Task getRawTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("AssignmentTask not found with id: " + id));
    }

    // Helper method to map Entity to DTO safely
    private TaskResponse mapToResponse(Task task) {
        String url = "";
        
        // Safely check if the image exists before getting the URL
        if (task.getImage() != null) {
            url = task.getImage().getImageUrl();
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .taskKey(task.getTaskKey())
                .category(task.getCategory())
                .orderIndex(task.getOrderIndex())
                .imageUrl(url)
                .build();
    }
}
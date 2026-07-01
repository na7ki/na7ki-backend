package com.na7ki.backend.domain.exercise.Service;

import com.na7ki.backend.domain.exercise.dto.ExerciseItemResponse;
import com.na7ki.backend.domain.exercise.Entity.ExerciseItem;
import com.na7ki.backend.domain.exercise.Repository.ExerciseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseItemService {

    private final ExerciseItemRepository exerciseItemRepository;

    public ExerciseItemService(ExerciseItemRepository exerciseItemRepository) {
        this.exerciseItemRepository = exerciseItemRepository;
    }

    public List<ExerciseItemResponse> getAllItems() {
        return exerciseItemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ExerciseItemResponse> getItemsByTaskKey(String taskKey) {
        return exerciseItemRepository.findByTask_TaskKeyOrderByOrderIndexAsc(taskKey)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ExerciseItemResponse> getItemsByTaskId(Long taskId) {
        return exerciseItemRepository.findByTask_IdOrderByOrderIndexAsc(taskId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ExerciseItemResponse mapToResponse(ExerciseItem item) {
        String imageUrl = item.getImage() != null ? item.getImage().getImageUrl() : null;
        String soundUrl = item.getSound() != null ? item.getSound().getSoundUrl() : null;
        String taskKey = item.getTask() != null ? item.getTask().getTaskKey() : null;

        return ExerciseItemResponse.builder()
                .id(item.getId())
                .taskKey(taskKey)
                .key(item.getItemKey())
                .label(item.getLabel())
                .imageUrl(imageUrl)
                .soundUrl(soundUrl)
                .build();
    }
}
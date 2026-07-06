package com.na7ki.backend.domain.exercise.pronunciation.dto;

import com.na7ki.backend.domain.exercise.pronunciation.entity.PronunciationAttempt;
import org.springframework.stereotype.Component;

@Component
public class PronunciationAttemptMapper {

    public PronunciationAttempt toEntity(Long patientId, Integer wordId, AiVerifyResponse aiResponse) {
        PronunciationAttempt entity = new PronunciationAttempt();
        entity.setPatientId(patientId);
        entity.setWordId(wordId);
        entity.setProbability(aiResponse.getProbability());
        entity.setCorrect(Boolean.TRUE.equals(aiResponse.getIsCorrect()));
        entity.setThresholdUsed(aiResponse.getThresholdUsed());
        entity.setModelVersion(aiResponse.getModelVersion());
        return entity;
    }

    public PronunciationAttemptResponse toResponse(PronunciationAttempt entity) {
        return PronunciationAttemptResponse.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .wordId(entity.getWordId())
                .probability(entity.getProbability())
                .correct(entity.isCorrect())
                .thresholdUsed(entity.getThresholdUsed())
                .modelVersion(entity.getModelVersion())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

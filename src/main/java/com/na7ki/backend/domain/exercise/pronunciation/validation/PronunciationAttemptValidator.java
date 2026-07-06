package com.na7ki.backend.domain.exercise.pronunciation.validation;

import com.na7ki.backend.domain.exercise.pronunciation.exception.PronunciationValidationException;
import com.na7ki.backend.domain.user.repository.PatientRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class PronunciationAttemptValidator {

    private final PatientRepository patientRepository;

    public PronunciationAttemptValidator(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void validate(Long patientId, Integer wordId, MultipartFile audio) {
        List<String> errors = new ArrayList<>();

        if (!patientRepository.existsById(patientId)) {
            errors.add("Patient not found: " + patientId);
        }

        if (wordId == null) {
            errors.add("wordId is required");
        }

        if (audio == null || audio.isEmpty()) {
            errors.add("audio file is required");
        }

        if (!errors.isEmpty()) {
            throw new PronunciationValidationException(errors);
        }
    }
}

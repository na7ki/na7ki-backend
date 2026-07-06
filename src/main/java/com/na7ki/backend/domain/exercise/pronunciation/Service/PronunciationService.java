package com.na7ki.backend.domain.exercise.pronunciation.Service;

import com.na7ki.backend.domain.exercise.pronunciation.Repository.PronunciationAttemptRepository;
import com.na7ki.backend.domain.exercise.pronunciation.dto.AiVerifyResponse;
import com.na7ki.backend.domain.exercise.pronunciation.dto.PronunciationAttemptMapper;
import com.na7ki.backend.domain.exercise.pronunciation.dto.PronunciationAttemptResponse;
import com.na7ki.backend.domain.exercise.pronunciation.entity.PronunciationAttempt;
import com.na7ki.backend.domain.exercise.pronunciation.exception.AiServiceUnavailableException;
import com.na7ki.backend.domain.exercise.pronunciation.validation.PronunciationAttemptValidator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PronunciationService {

    private final RestClient aiServiceRestClient;
    private final PronunciationAttemptRepository repository;
    private final PronunciationAttemptValidator validator;
    private final PronunciationAttemptMapper mapper;

    public PronunciationService(
            RestClient aiServiceRestClient,
            PronunciationAttemptRepository repository,
            PronunciationAttemptValidator validator,
            PronunciationAttemptMapper mapper
    ) {
        this.aiServiceRestClient = aiServiceRestClient;
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Transactional
    public PronunciationAttemptResponse verify(Long patientId, Integer wordId, MultipartFile audio) {
        validator.validate(patientId, wordId, audio);

        AiVerifyResponse aiResponse = callAiService(wordId, audio);

        PronunciationAttempt entity = mapper.toEntity(patientId, wordId, aiResponse);
        PronunciationAttempt saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    public List<PronunciationAttemptResponse> getForPatient(Long patientId) {
        return repository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    private AiVerifyResponse callAiService(Integer wordId, MultipartFile audio) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("audio", toResource(audio));
        body.add("word_id", String.valueOf(wordId));

        try {
            return aiServiceRestClient.post()
                    .uri("/verify")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new AiServiceUnavailableException(
                                "AI service returned " + response.getStatusCode() + " for /verify");
                    })
                    .body(AiVerifyResponse.class);
        } catch (ResourceAccessException e) {
            throw new AiServiceUnavailableException("Could not reach the AI service", e);
        }
    }

    private ByteArrayResource toResource(MultipartFile audio) {
        try {
            byte[] bytes = audio.getBytes();
            return new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return audio.getOriginalFilename() != null ? audio.getOriginalFilename() : "audio";
                }
            };
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read uploaded audio file", e);
        }
    }
}

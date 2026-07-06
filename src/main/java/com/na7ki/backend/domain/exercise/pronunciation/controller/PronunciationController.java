package com.na7ki.backend.domain.exercise.pronunciation.controller;

import com.na7ki.backend.domain.exercise.pronunciation.Service.PronunciationService;
import com.na7ki.backend.domain.exercise.pronunciation.dto.PronunciationAttemptResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * POST /api/patients/{patientId}/pronunciation-attempts — record + verify a spoken word
 * GET  /api/patients/{patientId}/pronunciation-attempts — fetch attempt history for a patient
 *
 * Auth (Bearer token) is enforced upstream via Spring Security filter chain.
 */
@RestController
@RequestMapping("/api/patients/{patientId}/pronunciation-attempts")
public class PronunciationController {

    private final PronunciationService pronunciationService;

    public PronunciationController(PronunciationService pronunciationService) {
        this.pronunciationService = pronunciationService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PronunciationAttemptResponse> verify(
            @PathVariable Long patientId,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam("wordId") Integer wordId
    ) {
        PronunciationAttemptResponse response = pronunciationService.verify(patientId, wordId, audio);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PronunciationAttemptResponse>> getForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(pronunciationService.getForPatient(patientId));
    }
}

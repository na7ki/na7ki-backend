package com.na7ki.backend.exercise.controller;

import com.na7ki.backend.exercise.Service.ExerciseService;
import com.na7ki.backend.exercise.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExerciseController {

    private final ExerciseService exerciseService;

    // Get all packages
    @GetMapping("/packages")
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        return ResponseEntity.ok(exerciseService.getAllPackages());
    }

    // Get package by ID
    @GetMapping("/packages/{packageId}")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable Long packageId) {
        return ResponseEntity.ok(exerciseService.getPackageById(packageId));
    }

    // Get questions for a package
    @GetMapping("/packages/{packageId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByPackage(@PathVariable Long packageId) {
        return ResponseEntity.ok(exerciseService.getQuestionsByPackage(packageId));
    }

    // Submit session and get results
    @PostMapping("/submit-session")
    public ResponseEntity<SessionResultDTO> submitSession(@RequestBody SubmitSessionDTO request) {
        SessionResultDTO result = exerciseService.submitSession(request);
        return ResponseEntity.ok(result);
    }

    // Check answer and return immediate feedback (for voice feedback)
    @PostMapping("/check-answer")
    public ResponseEntity<AnswerFeedbackDTO> checkAnswer(@RequestBody AnswerCheckDTO request) {
        AnswerFeedbackDTO feedback = exerciseService.checkAnswer(request);
        return ResponseEntity.ok(feedback);
    }

    // Get specific user session with results
    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<UserSessionDetailDTO> getUserSession(@PathVariable Long sessionId) {
        UserSessionDetailDTO session = exerciseService.getUserSession(sessionId);
        return ResponseEntity.ok(session);
    }

    // Get all sessions for a user
    @GetMapping("/users/{userId}/sessions")
    public ResponseEntity<List<UserSessionDetailDTO>> getUserSessions(@PathVariable Long userId) {
        List<UserSessionDetailDTO> sessions = exerciseService.getUserSessions(userId);
        return ResponseEntity.ok(sessions);
    }

    // Get latest session for a user
    @GetMapping("/users/{userId}/sessions/latest")
    public ResponseEntity<UserSessionDetailDTO> getLatestUserSession(@PathVariable Long userId) {
        UserSessionDetailDTO session = exerciseService.getLatestUserSession(userId);
        return ResponseEntity.ok(session);
    }
    @GetMapping("/questions/{questionId}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long questionId) {
        QuestionDTO questionDTO = exerciseService.getQuestionById(questionId);
        return ResponseEntity.ok(questionDTO);
    }
}

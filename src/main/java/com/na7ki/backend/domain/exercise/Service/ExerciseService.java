package com.na7ki.backend.domain.exercise.Service;

import com.na7ki.backend.domain.exercise.Entity.*;
import com.na7ki.backend.domain.exercise.Repository.*;
import com.na7ki.backend.domain.exercise.dto.*;
import com.na7ki.backend.domain.exercise.exception.PackageNotFoundException;
import com.na7ki.backend.domain.exercise.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final PackagesRepository packagesRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserSessionRepository userSessionRepository;
    private final UserAnswerRepository userAnswerRepository;

    // Get all packages
    public List<PackageDTO> getAllPackages() {
        return packagesRepository.findAll().stream()
                .map(this::convertPackageToDTO)
                .collect(Collectors.toList());
    }

    // Get package by ID with questions
    public PackageDTO getPackageById(Long packageId) {
        Packages pkg = packagesRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException("AssignmentPackage not found with id: " + packageId));
        return convertPackageToDTO(pkg);
    }

    // Get questions for a package
    public List<QuestionDTO> getQuestionsByPackage(Long packageId) {
        return questionRepository.findByPkgIdOrderByOrderIndex(packageId)
                .stream()
                .map(this::convertQuestionToDTO)
                .collect(Collectors.toList());
    }

   // Get a specific question by its ID
    public QuestionDTO getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("AssignmentQuestion not found with id: " + questionId));
        return convertQuestionToDTO(question);
    }

    public Question getRawQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("AssignmentQuestion not found with id: " + questionId));
    }

    // Submit session and calculate score
    @Transactional
    public SessionResultDTO submitSession(SubmitSessionDTO request) {
        // Verify package exists
        Packages pkg = packagesRepository.findById(request.getPackageId())
                .orElseThrow(() -> new RuntimeException("AssignmentPackage not found"));

        // Create user session
        UserSession session = new UserSession();
        session.setUserId(request.getUserId());
        session.setPkg(pkg);
        session.setTotalQuestions(request.getAnswers().size());
        UserSession savedSession = userSessionRepository.save(session);

        // Process answers
        int score = 0;
        List<QuestionResultDTO> results = request.getAnswers().stream()
                .map(answer -> {
                    Question question = questionRepository.findById(answer.getQuestionId())
                            .orElseThrow(() -> new RuntimeException("AssignmentQuestion not found"));
                    Choice choice = choiceRepository.findById(answer.getChoiceId())
                            .orElseThrow(() -> new RuntimeException("Choice not found"));

                    boolean isCorrect = question.getCorrectAnswer().equals(choice.getType());

                    // Save user answer
                    UserAnswer userAnswer = new UserAnswer();
                    userAnswer.setSession(savedSession);
                    userAnswer.setQuestion(question);
                    userAnswer.setChoice(choice);
                    userAnswer.setCorrect(isCorrect);
                    userAnswerRepository.save(userAnswer);

                    return new QuestionResultDTO(
                            question.getId(),
                            question.getQuestionText(),
                            question.getCorrectAnswer(),
                            choice.getType(),
                            isCorrect
                    );
                })
                .collect(Collectors.toList());

        // Calculate score
        score = (int) results.stream()
                .filter(QuestionResultDTO::getIsCorrect)
                .count();

        // Update session
        savedSession.setScore(score);
        userSessionRepository.save(savedSession);

        // Calculate percentage
        double percentage = request.getAnswers().isEmpty() ? 0 :
                Math.round((score * 100.0 / request.getAnswers().size()) * 10.0) / 10.0;

        return new SessionResultDTO(
                savedSession.getId(),
                score,
                request.getAnswers().size(),
                percentage,
                results
        );
    }

    // Helper methods
    private PackageDTO convertPackageToDTO(Packages pkg) {
        ImageDTO coverImage = null;
        if (pkg.getCoverImage() != null) {
            coverImage = new ImageDTO(
                    pkg.getCoverImage().getId(),
                    pkg.getCoverImage().getImageUrl(),
                    pkg.getCoverImage().getImageName(),
                    pkg.getCoverImage().getFolderName()
            );
        }
        return new PackageDTO(
                pkg.getId(),
                pkg.getTitle(),
                pkg.getDescription(),
                coverImage
        );
    }

    private QuestionDTO convertQuestionToDTO(Question question) {
        List<ChoiceDTO> choices = question.getChoices().stream()
                .map(choice -> new ChoiceDTO(
                        choice.getId(),
                        new ImageDTO(
                                choice.getImage().getId(),
                                choice.getImage().getImageUrl(),
                                choice.getImage().getImageName(),
                                choice.getImage().getFolderName()
                        ),
                        choice.getType()
                ))
                .collect(Collectors.toList());

        return new QuestionDTO(
                question.getId(),
                question.getQuestionText(),
                question.getCorrectAnswer(),
                choices
        );
    }

    // Check single answer and return feedback (for immediate voice feedback)
    public AnswerFeedbackDTO checkAnswer(AnswerCheckDTO request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("AssignmentQuestion not found"));
        Choice choice = choiceRepository.findById(request.getChoiceId())
                .orElseThrow(() -> new RuntimeException("Choice not found"));

        boolean isCorrect = question.getCorrectAnswer().equals(choice.getType());

        return new AnswerFeedbackDTO(
                question.getId(),
                choice.getId(),
                question.getQuestionText(),
                question.getCorrectAnswer(),
                choice.getType(),
                isCorrect,
                isCorrect ? "correct" : "incorrect"
        );
    }

    // Get user session by ID with all results
    public UserSessionDetailDTO getUserSession(Long sessionId) {
        UserSession session = userSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        List<QuestionResultDTO> results = session.getAnswers().stream()
                .map(answer -> new QuestionResultDTO(
                        answer.getQuestion().getId(),
                        answer.getQuestion().getQuestionText(),
                        answer.getQuestion().getCorrectAnswer(),
                        answer.getChoice().getType(),
                        answer.isCorrect()
                ))
                .collect(Collectors.toList());

        double percentage = session.getTotalQuestions() == 0 ? 0 :
                Math.round((session.getScore() * 100.0 / session.getTotalQuestions()) * 10.0) / 10.0;

        return new UserSessionDetailDTO(
                session.getId(),
                session.getUserId(),
                session.getPkg().getId(),
                session.getPkg().getTitle(),
                session.getScore(),
                session.getTotalQuestions(),
                percentage,
                session.getCreatedAt(),
                results
        );
    }

    // Get all sessions for a user
    public List<UserSessionDetailDTO> getUserSessions(Long userId) {
        return userSessionRepository.findByUserId(userId)
                .stream()
                .map(this::convertSessionToDetailDTO)
                .collect(Collectors.toList());
    }

    // Get latest session for a user
    public UserSessionDetailDTO getLatestUserSession(Long userId) {
        List<UserSession> sessions = userSessionRepository.findByUserId(userId);
        if (sessions.isEmpty()) {
            throw new RuntimeException("No sessions found for user");
        }
        return convertSessionToDetailDTO(sessions.get(sessions.size() - 1));
    }

    private UserSessionDetailDTO convertSessionToDetailDTO(UserSession session) {
        List<QuestionResultDTO> results = session.getAnswers().stream()
                .map(answer -> new QuestionResultDTO(
                        answer.getQuestion().getId(),
                        answer.getQuestion().getQuestionText(),
                        answer.getQuestion().getCorrectAnswer(),
                        answer.getChoice().getType(),
                        answer.isCorrect()
                ))
                .collect(Collectors.toList());

        double percentage = session.getTotalQuestions() == 0 ? 0 :
                Math.round((session.getScore() * 100.0 / session.getTotalQuestions()) * 10.0) / 10.0;

        return new UserSessionDetailDTO(
                session.getId(),
                session.getUserId(),
                session.getPkg().getId(),
                session.getPkg().getTitle(),
                session.getScore(),
                session.getTotalQuestions(),
                percentage,
                session.getCreatedAt(),
                results
        );
    }
}

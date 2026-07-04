package com.na7ki.backend.core.exception;

import com.na7ki.backend.domain.exercise.exception.PackageNotFoundException;
import com.na7ki.backend.domain.exercise.exception.QuestionNotFoundException;
import com.na7ki.backend.domain.exercise.exception.TaskNotFoundException;
import com.na7ki.backend.domain.exercise.exception.TaskResultValidationException;
import com.na7ki.backend.domain.user.exception.SpecificIdNotAssociatedWithAnyUserException;
import com.na7ki.backend.exercise_management.exception.NothingChosenToBeAssignedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExerciseExceptionHandler {

    @ExceptionHandler(SpecificIdNotAssociatedWithAnyUserException.class)
    public ResponseEntity<String> handleSpecificIdNotAssociatedWithAnyUserException(SpecificIdNotAssociatedWithAnyUserException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PackageNotFoundException.class)
    public ResponseEntity<String> handlePackageNotFoundException(PackageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<String> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NothingChosenToBeAssignedException.class)
    public ResponseEntity<String> handleNothingChosenToBeAssignedException(NothingChosenToBeAssignedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TaskResultValidationException.class)
    public ResponseEntity<List<String>> handleTaskResultValidationException(TaskResultValidationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getErrors());
    }

}

package com.na7ki.backend.specialist_actions.manage_tasks.entity;

import com.na7ki.backend.domain.exercise.Entity.Question;
import com.na7ki.backend.domain.exercise.Entity.Task;
import com.na7ki.backend.specialist_actions.manage_tasks.entity.enums.ExerciseType;
import jakarta.persistence.*;

@Entity
public class AssignedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseType type; // QUESTION or TASK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question; // null if type = TASK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task; // null if type = QUESTION

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

}

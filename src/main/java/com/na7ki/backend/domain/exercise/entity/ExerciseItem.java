package com.na7ki.backend.domain.exercise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercise_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    private String itemKey;     // English identifier, e.g. 'apple', 'bear_clap' — case-sensitive, matches Flutter's `key`
    private String label;       // Arabic display text — nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sound_id")
    private Sound sound;

    private Integer orderIndex;
}

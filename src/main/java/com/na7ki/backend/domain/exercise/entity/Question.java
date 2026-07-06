package com.na7ki.backend.domain.exercise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Packages pkg;

    private String questionText;
    private String correctAnswer;  // "SINGULAR" or "PLURAL"
    private Integer orderIndex;

    // Used by single-image naming exercises (e.g. package 4); NULL for choice-based packages
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Choice> choices;
}

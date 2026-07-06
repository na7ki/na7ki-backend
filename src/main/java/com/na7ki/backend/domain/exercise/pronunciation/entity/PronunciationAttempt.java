package com.na7ki.backend.domain.exercise.pronunciation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "pronunciation_attempts",
       indexes = {
           @Index(name = "idx_pronunciation_attempts_patient", columnList = "patient_id"),
           @Index(name = "idx_pronunciation_attempts_patient_created", columnList = "patient_id, created_at")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PronunciationAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    // References app/artifacts/words_id.json in the na7ki-ai service, not any Java entity.
    @Column(name = "word_id", nullable = false)
    private Integer wordId;

    @Column(nullable = false)
    private Double probability;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    @Column(name = "threshold_used", nullable = false)
    private Double thresholdUsed;

    @Column(name = "model_version", nullable = false)
    private String modelVersion;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}

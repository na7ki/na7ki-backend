package com.na7ki.backend.domain.exercise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

@Entity
@Table(name = "task_results",
       indexes = {
           @Index(name = "idx_task_results_patient", columnList = "patient_id"),
           @Index(name = "idx_task_results_task", columnList = "task_id"),
           @Index(name = "idx_task_results_started", columnList = "patient_id, started_at")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uq_task_results_idempotency",
                              columnNames = {"patient_id", "task_id", "started_at"})
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "started_at", nullable = false)
    private OffsetDateTime startedAt;

    @Column(name = "completed_at", nullable = false)
    private OffsetDateTime completedAt;

    @Column(nullable = false)
    private boolean completed;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @Column(name = "total_rounds", nullable = false)
    private Integer totalRounds;

    @Column(name = "correct_rounds", nullable = false)
    private Integer correctRounds;

    @Column(precision = 5, scale = 4)
    private BigDecimal accuracy;

    @Column(name = "attempts_count", nullable = false)
    private Integer attemptsCount;

    @Column(name = "avg_reaction_ms")
    private Integer avgReactionTimeMs;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "error_breakdown", columnDefinition = "json", nullable = false)
    private Map<String, Integer> errorBreakdown;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Object> extra;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}
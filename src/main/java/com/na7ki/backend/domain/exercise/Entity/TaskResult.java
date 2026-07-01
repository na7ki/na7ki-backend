package com.na7ki.backend.domain.exercise.Entity;

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
           @Index(name = "idx_task_results_case", columnList = "case_id"),
           @Index(name = "idx_task_results_task", columnList = "task_id"),
           @Index(name = "idx_task_results_started", columnList = "case_id, started_at")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uq_task_results_idempotency",
                              columnNames = {"case_id", "task_id", "started_at"})
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private Cases caseEntity;          // confirmed FK now — app never sends null caseId

    @Column(name = "task_id", nullable = false)
    private Integer taskId;            // NOT a FK — validated against known set (101-109) in service layer

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
    private BigDecimal accuracy;       // null only when totalRounds = 0

    @Column(name = "attempts_count", nullable = false)
    private Integer attemptsCount;

    @Column(name = "avg_reaction_ms")
    private Integer avgReactionTimeMs; // null allowed (e.g. trace_path always null)

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "error_breakdown", columnDefinition = "json", nullable = false)
    private Map<String, Integer> errorBreakdown;  // GIN-indexed for population-wide queries

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Object> extra;            // shape varies by taskKey — sound_match.replayCount, trace_path.resetsUsed, color_sort.mistakes

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}
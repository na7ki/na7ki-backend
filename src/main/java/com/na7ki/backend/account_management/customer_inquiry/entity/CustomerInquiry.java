package com.na7ki.backend.account_management.customer_inquiry.entity;

import com.na7ki.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@MappedSuperclass // Or @Entity depending on your previous choice
@Getter
@Setter
@SuperBuilder // CRITICAL: Use SuperBuilder instead of Builder
@NoArgsConstructor
public abstract class CustomerInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String inquiryContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquirer_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User inquirer;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAtDate;

}

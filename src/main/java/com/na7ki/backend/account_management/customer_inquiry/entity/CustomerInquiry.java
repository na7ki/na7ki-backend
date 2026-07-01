package com.na7ki.backend.account_management.customer_inquiry.entity;

import com.na7ki.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class CustomerInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String inquiryContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquirer_id", referencedColumnName = "userId", nullable = false, updatable = false)
    private User inquirer;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAtDate;

}

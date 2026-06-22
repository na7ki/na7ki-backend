package com.na7ki.backend.account_management.customer_inquiry.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder // Matches the parent
@NoArgsConstructor
public class BugReport extends CustomerInquiry {
}

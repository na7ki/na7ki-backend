package com.na7ki.backend.account_management.customer_inquiry.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder // Matches the parent
@NoArgsConstructor
public class ContactRequest extends CustomerInquiry {
}

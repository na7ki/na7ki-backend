package com.na7ki.backend.account_management.customer_inquiry.repository;


import com.na7ki.backend.account_management.customer_inquiry.entity.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRequestRepository extends JpaRepository<ContactRequest, Long> {
}

package com.na7ki.backend.account_management.customer_inquiry.repository;

import com.na7ki.backend.account_management.customer_inquiry.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugReportRepository extends JpaRepository<BugReport, Long> {
}

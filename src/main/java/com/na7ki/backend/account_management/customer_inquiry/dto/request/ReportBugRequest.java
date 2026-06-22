package com.na7ki.backend.account_management.customer_inquiry.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReportBugRequest(

        @NotBlank(message = "A content for the bug description is required")
        String bugDescription

) {
}

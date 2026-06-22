package com.na7ki.backend.account_management.customer_inquiry.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ContactUsRequest(

        @NotBlank(message = "A content for the contact request is required")
        String userMessage

) {
}

package com.na7ki.backend.account_management.contact_request.dto;

import jakarta.validation.constraints.NotBlank;

public record ContactUsRequest(

        @NotBlank(message = "A content for the contact request is required")
        String userMessage
) {
}

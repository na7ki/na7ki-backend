package com.na7ki.backend.auth.forgot_password.dto.response;

public record ForgotPasswordResponse(

        String providedEmail,
        Boolean isSent,
        String message

) {
}

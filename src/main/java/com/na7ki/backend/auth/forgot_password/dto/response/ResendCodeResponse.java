package com.na7ki.backend.auth.forgot_password.dto.response;

public record ResendCodeResponse(

        String ProvidedEmail,
        Boolean isResent,
        String message

) {
}

package com.na7ki.backend.auth.forgot_password.dto.response;

public record SendCodeResponse(

        String ProvidedEmail,
        Boolean isSent,
        String message

) {
}

package com.na7ki.backend.auth.dto.response.forgotpassword;

public record ForgotPasswordResponse(

        String providedEmail,

        String message

) {
}

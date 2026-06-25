package com.na7ki.backend.auth.dto.response.forgotpassword;

public record ResendCodeResponse(

        String email,

        Boolean isResent,

        String message

) {
}

package com.na7ki.backend.auth.dto.response.forgotpassword;

public record VerifyCodeResponse(

        String associatedEmail,

        Boolean doesMatch

) {
}

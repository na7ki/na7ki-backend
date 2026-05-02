package com.na7ki.backend.auth.dto.response;

import lombok.AllArgsConstructor;

import java.util.List;

public record AuthResponse(

    String jwt,
    String email,
    List<String> roles

) {
}

package com.na7ki.backend.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String extractUsername(String token) {
        return "";
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        return true;

    }
}

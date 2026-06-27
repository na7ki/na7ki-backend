package com.na7ki.backend.domain.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class PasswordGenerator {

    private final PasswordEncoder encoder;

    private final Short RANDOM_PASSWORD_LENGTH = 5;





    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public String generateRandomRawPassword() {

        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();

        StringBuilder generatedRawPassword = new StringBuilder(RANDOM_PASSWORD_LENGTH);
        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++) {
            generatedRawPassword.append(characters.charAt(random.nextInt(characters.length())));
        }

        return generatedRawPassword.toString();
    }

}

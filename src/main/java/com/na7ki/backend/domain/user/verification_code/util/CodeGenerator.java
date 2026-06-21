package com.na7ki.backend.domain.user.verification_code.util;

import java.security.SecureRandom;

public class CodeGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    // Generates a 4-digit random string from 0000 to 9999.
    public static String generateFourDigitCode() {
        int number = secureRandom.nextInt(10000);
        return String.format("%04d", number);    // Pads with zeros to stay 4 digits
    }

}

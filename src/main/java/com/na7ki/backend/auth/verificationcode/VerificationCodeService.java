package com.na7ki.backend.auth.verificationcode;

import com.na7ki.backend.auth.verificationcode.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeExpiredException;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeMismatchException;
import com.na7ki.backend.auth.verificationcode.helper.CodeGenerator;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;



    public String createCode (User associateUser) {
        String resetVerificationCode = CodeGenerator.generateFourDigitCode();

        VerificationCode code = VerificationCode.builder()
                .fourDigitCode(resetVerificationCode)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(associateUser)
                .build();

        verificationCodeRepository.save(code);

        return resetVerificationCode;
    }

    public boolean verifyCode (String code, String associateUserEmail) {

        VerificationCode targetCode = verificationCodeRepository.findByUserEmail(associateUserEmail)
                .orElseThrow(() -> new NoVerificationCodeForThisEmail("No verification code is associated with the provided email"));

        if (!targetCode.getFourDigitCode().equals(code)) {
            throw new VerificationCodeMismatchException("The code you entered is incorrect");
        }

        if (targetCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new VerificationCodeExpiredException("This code has expired. Please request a new one");
        }

        verificationCodeRepository.delete(targetCode);

        return true;
    }

}

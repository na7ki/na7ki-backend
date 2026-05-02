package com.na7ki.backend.auth.verificationcode;

import com.na7ki.backend.auth.verificationcode.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeExpiredException;
import com.na7ki.backend.auth.verificationcode.exception.VerificationCodeMismatchException;
import com.na7ki.backend.auth.verificationcode.helper.CodeGenerator;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;

    private static final short EXPIRATION_DURATION_MINUTES = 2;



    public String createCode (User associateUser) {
        String resetVerificationCode = CodeGenerator.generateFourDigitCode();

        VerificationCode code = VerificationCode.builder()
                .fourDigitCode(resetVerificationCode)
                .expiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_DURATION_MINUTES))
                .user(associateUser)
                .build();

        verificationCodeRepository.save(code);

        return resetVerificationCode;
    }

    public boolean verifyCode (String code, String associatedUserEmail) {

        User associatedUser = userRepository.findByEmail(associatedUserEmail)
                .orElseThrow(() -> new NoVerificationCodeForThisEmail("No verification code is associated with the provided email"));

        VerificationCode targetCode = associatedUser.getVerificationCode();

        if (!targetCode.getFourDigitCode().equals(code)) {
            throw new VerificationCodeMismatchException("The code you entered is incorrect");
        }

        if (targetCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new VerificationCodeExpiredException("This code has expired. Please request a new one");
        }

        deleteCode(targetCode);

        return true;
    }

    public VerificationCode getCodeOfUser (String potentialUserEmail) {
        VerificationCode targetCode = verificationCodeRepository.findByUserEmail(potentialUserEmail)
                .orElseThrow(() -> new NoVerificationCodeForThisEmail("No verification code is associated with the provided email"));

        return targetCode;
    }

    public void deleteCode (VerificationCode code) {
        User user = code.getUser();
        user.setVerificationCode(null);
        code.setUser(null);
        verificationCodeRepository.delete(code);
        verificationCodeRepository.flush();
    }

}

package com.na7ki.backend.domain.user.verification_code;

import com.na7ki.backend.domain.user.verification_code.enums.VerificationCodeStatus;
import com.na7ki.backend.domain.user.verification_code.enums.VerifyCodeStatus;
import com.na7ki.backend.domain.user.verification_code.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.domain.user.verification_code.exception.VerificationCodeForThisEmailAlreadyExistsException;
import com.na7ki.backend.domain.user.verification_code.util.CodeGenerator;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final UserService userService;


    private static final short EXPIRATION_DURATION_MINUTES = 3;





    public String createCode (User associatedUser) {
        String resetVerificationCode = CodeGenerator.generateFourDigitCode();

        VerificationCode code = VerificationCode.builder()
                .fourDigitCode(resetVerificationCode)
                .expiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_DURATION_MINUTES))
                .user(associatedUser)
                .build();

        verificationCodeRepository.save(code);

        return resetVerificationCode;
    }

    public void deleteCode (VerificationCode code) {
        User user = code.getUser();
        user.setVerificationCode(null);
        code.setUser(null);
        verificationCodeRepository.delete(code);
        verificationCodeRepository.flush();
    }





    public String requestCode(User user, Boolean replaceExistingCode) {
        VerificationCodeStatus status = verificationCodeStatus(user);

        if (status == VerificationCodeStatus.VALID) {
            if (!replaceExistingCode) {
                throw new VerificationCodeForThisEmailAlreadyExistsException("There is already an active verification code for this email. Check your email to see your code or try again to get a new code.");
            }
            deleteCode(user.getVerificationCode());
        } else if (status == VerificationCodeStatus.EXPIRED) {
            deleteCode(user.getVerificationCode());
        }

        return createCode(user);
    }

    public VerifyCodeStatus verifyCode (User user, String code, boolean isResetPassword) {
        switch (verificationCodeStatus(user))
        {
            case VerificationCodeStatus.NO_VERIFICATION_CODE:
                throw new NoVerificationCodeForThisEmail("No verification code for this email. Please request a code");

            case VerificationCodeStatus.EXPIRED:
                return VerifyCodeStatus.EXPIRED;

            case VerificationCodeStatus.VALID:
                boolean doesMatch = user.getVerificationCode().getFourDigitCode().equals(code);
                if (doesMatch) {
                    if (isResetPassword)
                    {
                        deleteCode(user.getVerificationCode());
                    }
                    return VerifyCodeStatus.MATCH;
                }
                else {
                    return VerifyCodeStatus.DOES_NOT_MATCH;
                }

            default:
                throw new IllegalStateException("Unexpected verification code status");
        }
    }





    private VerificationCodeStatus verificationCodeStatus(User user) {

        VerificationCode potentialAssociatedCode = user.getVerificationCode();

        if (potentialAssociatedCode == null) return VerificationCodeStatus.NO_VERIFICATION_CODE;
        if (potentialAssociatedCode.isExpired()) return VerificationCodeStatus.EXPIRED;
        return VerificationCodeStatus.VALID;
    }

}

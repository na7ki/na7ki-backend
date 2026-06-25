package com.na7ki.backend.domain.user.verification_code;

import com.na7ki.backend.domain.user.verification_code.enums.VerificationCodeStatus;
import com.na7ki.backend.domain.user.verification_code.enums.VerifyCodeStatus;
import com.na7ki.backend.domain.user.verification_code.exception.NoVerificationCodeForThisEmail;
import com.na7ki.backend.domain.user.verification_code.exception.VerificationCodeForThisEmailAlreadyExistsException;
import com.na7ki.backend.domain.user.verification_code.util.CodeGenerator;
import com.na7ki.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    @Value("${vc.expiration.basic}")
    private static final short EXPIRATION_DURATION_MINUTES = 3;

    @Value("${vc.expiration.additional}")
    private static final short EXPIRATION_DURATION_AFTER_VERIFICATION_MINUTES = 5;





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

    public void deleteCode (User associatedUser) {
        VerificationCode associatedCode = associatedUser.getVerificationCode();
        if (associatedCode == null)
        {
            return;
        }
        associatedUser.setVerificationCode(null);
        associatedCode.setUser(null);
        verificationCodeRepository.delete(associatedCode);
        verificationCodeRepository.flush();
    }





    public String requestCode(User user, Boolean replaceExistingCode) {
        VerificationCodeStatus status = verificationCodeStatus(user);

        if (status == VerificationCodeStatus.VALID) {
            if (!replaceExistingCode) {
                throw new VerificationCodeForThisEmailAlreadyExistsException("There is already an active verification code for this email. Check your email to see your code or try again to get a new code.");
            }
            deleteCode(user);
        } else if (status == VerificationCodeStatus.EXPIRED) {
            deleteCode(user);
        }

        return createCode(user);
    }

    public VerifyCodeStatus verifyCode (User user, String code, boolean doDeleteOnMatch) {
        switch (verificationCodeStatus(user))
        {
            case VerificationCodeStatus.NO_VERIFICATION_CODE:
                throw new NoVerificationCodeForThisEmail("No verification code for this email. Please request a code");

            case VerificationCodeStatus.EXPIRED:
                //this call to deleteCode will only be persisted when the caller is verifyCode
                //it won't be persisted when the caller is resetPassword, because it throws and exception, so the deletion transaction rolls back
                //this means that expired codes won't get cleaned by resetPassword. The elegant way to handle this is to make an async job that runs every set time to delete expired codes. Do this if needed.
                deleteCode(user);
                return VerifyCodeStatus.EXPIRED;

            case VerificationCodeStatus.VALID:
                boolean doesMatch = user.getVerificationCode().getFourDigitCode().equals(code);
                if (doesMatch) {
                    if (doDeleteOnMatch)
                    {
                        deleteCode(user);
                    } else {
                        //to allow the user some time between verifying the code and resetting the password
                        user.getVerificationCode().setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_DURATION_AFTER_VERIFICATION_MINUTES));
                        verificationCodeRepository.save(user.getVerificationCode());
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

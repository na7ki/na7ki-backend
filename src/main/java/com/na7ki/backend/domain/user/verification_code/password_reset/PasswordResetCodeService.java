package com.na7ki.backend.domain.user.verification_code.password_reset;

import com.na7ki.backend.core.email.EmailService;
import com.na7ki.backend.domain.user.UserService;
import com.na7ki.backend.domain.user.entity.User;
import com.na7ki.backend.domain.user.verification_code.VerificationCodeService;
import com.na7ki.backend.domain.user.verification_code.auxiliary.VerifyCodeStatus;
import com.na7ki.backend.domain.user.verification_code.dto.VerifyCodeResult;
import com.na7ki.backend.domain.user.verification_code.exception.InvalidVerificationCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetCodeService {

    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final UserService userService;





    @Transactional
    public void sendCode(User user, boolean replaceExisting) {
        String code = verificationCodeService.requestCode(user, replaceExisting);
        emailService.sendVerificationCode(user.getEmail(), code);
    }

    @Transactional
    public VerifyCodeResult verifyCode(User user, String submittedCode, boolean deleteOnMatch) {
        VerifyCodeStatus status = verificationCodeService.verifyCode(user, submittedCode, deleteOnMatch);

        String message = switch (status) {
            case MATCH -> "The code has been verified successfully";
            case DOES_NOT_MATCH -> "The code you entered is incorrect. Try again";
            case EXPIRED -> "The code has expired, please request a new code";
        };

        return new VerifyCodeResult(status, message);
    }

    @Transactional
    public void resetPassword(User user, String submittedCode, String newPassword) {
        VerifyCodeStatus status = verificationCodeService.verifyCode(user, submittedCode, true);
        if (status != VerifyCodeStatus.MATCH) {
            throw new InvalidVerificationCodeException("Invalid or expired verification code");
        }
        userService.updateUserPassword(user, newPassword);
    }

}

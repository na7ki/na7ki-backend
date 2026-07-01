package com.na7ki.backend.core.email;

import com.na7ki.backend.core.email.model.PatientPasswordEmail;
import com.na7ki.backend.core.email.model.VerificationCodeEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCode(String toEmail, VerificationCodeEmail input) {
        final String subject = "Verification code for password reset";
        final String body =
                "Hello, " + "\n\n" +
                "Your verification code is: " + input.code() + "\n\n" +
                "If you haven't requested this code or tried logging in on our platform, please ignore this email";

        sendEmail(toEmail, subject, body);
    }

    public void sendAddedPatientPassword(String toEmail, PatientPasswordEmail input) {
        final String subject = "Password of your new account";
        final String body =
                "Welcome to Na7ki, " + input.patientName() + "!\n\n" +
                "Your associated specialist Dr. " + input.associatedSpecialistName() + " has registered you on our platform, and this is the password of your account:    " + input.rawPassword() + "\n\n" +
                "Remember, you can always change your password from the profile tab. For now you can use only the password above to login.\n" +
                "We hope you enjoy our platform and we hope we assist your child in their amazing journey!" + "\n\n\n" +
                "If you haven't asked a speech therapist to register you on Na7ki, or believe this email is irrelevant to you, please ignore it";

        sendEmail(toEmail, subject, body);
    }

    private void sendEmail (String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}

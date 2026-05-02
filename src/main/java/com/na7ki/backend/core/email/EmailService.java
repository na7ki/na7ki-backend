package com.na7ki.backend.core.email;

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

    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Verification code for password reset");
        message.setText(
                "Hello, " + "\n\n" +
                "Your verification code is: " + code + "\n\n" +
                "If you haven't requested this code or tried loging in on our platform, please ignore this email"
        );
        mailSender.send(message);
    }
}

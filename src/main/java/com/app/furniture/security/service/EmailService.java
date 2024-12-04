package com.app.furniture.security.service;

import com.app.furniture.entity.ResetPasswordToken;
import com.app.furniture.entity.User;
import com.app.furniture.repository.ResetPasswordTokenRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    private final ResetPasswordTokenRepo emailTokenRepo;

    @Async
    public void sendEmailValidation(User user) throws MessagingException {
        String resetCode = generateAndSaveToken(user);
        sendEmail(user.getEmail(), user.fullName(), "activate_account",
                resetCode, "Reset password");
    }

    private void sendEmail(String to, String name, String emailTemplate,
                             String resetCode, String subject) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", name);
        properties.put("resetCode", resetCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("GMAIL_ACCOUNT");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(message);
    }



    private String generateAndSaveToken(User user) {
        String generatedCode = generateToken(6);
        ResetPasswordToken emailToken = ResetPasswordToken.builder()
                .token(generatedCode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        emailTokenRepo.save(emailToken);
        return generatedCode;
    }

    private String generateToken(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

}

package de.saarland.moebel.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import de.saarland.moebel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final SendGrid sendGrid;

    @Value("${app.email.from}")
    private String fromEmail;

    public EmailService(@Value("${SENDGRID_API_KEY}") String sendGridApiKey) {
        this.sendGrid = new SendGrid(sendGridApiKey);
    }

    public void sendPasswordResetEmail(User user, String resetLink) {
        String subject = "Password Reset Request for Saar Möbel";
        String textContent = String.format(
                "Hello, %s!\n\nYou requested a password reset. Please click the link below to set a new password:\n%s\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Best wishes, the Saar Möbel team!",
                user.getUsername(),
                resetLink
        );
        sendEmail(user.getEmail(), subject, textContent);
    }

    private void sendEmail(String toEmail, String subject, String textContent) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", textContent);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            logger.info("Email sent to {}. Subject: '{}'. Status code: {}", toEmail, subject, response.getStatusCode());
        } catch (IOException ex) {
            logger.error("Error sending email to {}: {}", toEmail, ex.getMessage());
            // В реальном приложении здесь может быть более сложная обработка ошибок
        }
    }
}
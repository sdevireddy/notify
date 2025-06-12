package com.zen.notify.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.zen.notify.exceptions.EmailSendException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sendtosankar@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            logger.info("Email successfully sent to {}", toEmail);
        } catch (MailException ex) {
            logger.error("Failed to send email to {}: {}", toEmail, ex.getMessage(), ex);
            throw new EmailSendException("Failed to send email. Please try again later.");
        } catch (Exception ex) {
            logger.error("Unexpected error while sending email to {}: {}", toEmail, ex.getMessage(), ex);
            throw new EmailSendException("Unexpected error occurred while sending email.");
        }
    }
    

        public void sendAccountCreationEmail(String toEmail, String password) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your Account Has Been Created");
            message.setText("Your account has been created successfully.\n\nTemporary Password: " + password + "\nPlease log in and change your password.");

            mailSender.send(message);
        }
        
        public void sendInvitationEmail(String toEmail, String tempPassword) {
            String subject = "Welcome! Set your password";
            String body = "You've been invited to join.\n\nYour temporary password is: " + tempPassword +
                          "\n\nPlease log in and reset your password.";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }
        
        public void sendFailureReportToAdmin(List<String> failedEmails) {
            String subject = "⚠️ Failed to Send Invitations";
            String body = "The following user invitations failed:\n\n" +
                          String.join("\n", failedEmails);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("sendtosankar@gmail.com");
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }

    }





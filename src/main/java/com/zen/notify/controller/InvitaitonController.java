package com.zen.notify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.PasswordGenerator;
import com.zen.notify.email.EmailService;
import com.zen.notify.entity.ZenUser;
import com.zen.notify.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crm")
public class InvitaitonController {

    private static final Logger log = LoggerFactory.getLogger(InvitaitonController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/send-invitations")
    public ResponseEntity<String> sendBulkInvitations(@RequestBody List<String> emails) {
        log.info("📨 Sending bulk invitations to {} email(s)", emails.size());

        List<String> failedEmails = new ArrayList<>();

        for (String email : emails) {
            try {
                Optional<ZenUser> existingUser = userRepository.findByEmail(email.trim());
                if (existingUser.isPresent()) {
                    log.warn("⚠️ Email {} already exists in DB. Skipping...", email);
                    failedEmails.add(email + " (already exists)");
                    continue;
                }

                String tempPassword = passwordGenerator.generate();
                ZenUser user = new ZenUser();
                user.setEmail(email.trim());
                user.setUsername(email.substring(0, email.indexOf('@')));
                user.setPassword(passwordEncoder.encode(tempPassword));
                user.setFirstLogin(true);
                user.setIsActive(true);

                userRepository.save(user);
                emailService.sendInvitationEmail(email, tempPassword);
                log.info("✅ Invitation sent successfully to {}", email);

            } catch (Exception e) {
                log.error("❌ Error sending invitation to {}: {}", email, e.getMessage(), e);
                failedEmails.add(email + " (error: " + e.getMessage() + ")");
            }
        }

        if (!failedEmails.isEmpty()) {
            log.warn("⚠️ Some invitations failed. Sending failure report to admin.");
            emailService.sendFailureReportToAdmin(failedEmails);
        }

        String result = "Invitations processed. Success: " + (emails.size() - failedEmails.size())
                + ", Failed: " + failedEmails.size();

        log.info("📊 Bulk invitation summary: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send-invitation")
    public ResponseEntity<String> sendSingleInvitation(@RequestBody String email) {
        log.info("📨 Sending single invitation to {}", email);
        try {
            Optional<ZenUser> existing = userRepository.findByEmail(email);
            if (existing.isPresent()) {
                String msg = "User with email " + email + " already exists.";
                log.warn("⚠️ {}", msg);
                emailService.sendFailureReportToAdmin(List.of(msg));
                return ResponseEntity.badRequest().body(msg);
            }

            String tempPassword = passwordGenerator.generate();
            ZenUser user = new ZenUser();
            user.setEmail(email);
            user.setUsername(email.substring(0, email.indexOf('@')));
            user.setPassword(passwordEncoder.encode(tempPassword));
            user.setFirstLogin(true);
            user.setIsActive(true);

            userRepository.save(user);
            emailService.sendInvitationEmail(user.getEmail(), tempPassword);
            log.info("✅ Invitation sent successfully to {}", user.getEmail());

            return ResponseEntity.ok("Invitation sent successfully to " + user.getEmail());
        } catch (Exception e) {
            String errorMsg = "Failed to invite " + email + ": " + e.getMessage();
            log.error("❌ {}", errorMsg, e);
            emailService.sendFailureReportToAdmin(List.of(errorMsg));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        log.info("🔐 Password reset requested for user: {}", request.getUsername());

        ZenUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("⚠️ User not found: {}", request.getUsername());
                    return new UsernameNotFoundException("User not found");
                });

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            log.warn("❌ Invalid old password for user: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setFirstLogin(false);
        userRepository.save(user);

        log.info("✅ Password changed successfully for user: {}", request.getUsername());
        return ResponseEntity.ok("Password changed successfully");
    }
}

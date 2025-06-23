package com.zen.notify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zen.notify.PasswordGenerator;
import com.zen.notify.email.EmailService;
import com.zen.notify.entity.ZenUser;
import com.zen.notify.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/crm")
public class InvitaitonController {
	
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private PasswordGenerator passwordGenerator;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/send-invitations")
	public ResponseEntity<String> sendBulkInvitations(@RequestBody List<String> emails) {
	    List<String> failedEmails = new ArrayList<>();

	    for (String email : emails) {
	        try {
	            Optional<ZenUser> existingUser = userRepository.findByEmail(email.trim());
	            if (existingUser.isPresent()) {
	                failedEmails.add(email + " (already exists)");
	                continue;
	            }

	            String tempPassword = passwordGenerator.generate();

	            ZenUser user = new ZenUser();
	            user.setEmail(email.trim());
	            user.setUsername(email.substring(0, email.indexOf('@'))); // simple username
	            user.setPassword(passwordEncoder.encode(tempPassword));
	            user.setFirstLogin(true);
	            user.setIsActive(true);

	            userRepository.save(user);
	            emailService.sendInvitationEmail(email, tempPassword);

	        } catch (Exception e) {
	            failedEmails.add(email + " (error: " + e.getMessage() + ")");
	        }
	    }

	    if (!failedEmails.isEmpty()) {
	        emailService.sendFailureReportToAdmin(failedEmails);
	    }

	    String result = "Invitations processed. Success: " + (emails.size() - failedEmails.size())
	                  + ", Failed: " + failedEmails.size();

	    return ResponseEntity.ok(result);
	}

	@PostMapping("/send-invitation")
	public ResponseEntity<String> sendSingleInvitation(@RequestBody String email) {
	    try {
	        Optional<ZenUser> existing = userRepository.findByEmail(email);
	        if (existing.isPresent()) {
	            String msg = "User with email " + email + " already exists.";
	            emailService.sendFailureReportToAdmin(List.of(msg));
	            return ResponseEntity.badRequest().body(msg);
	        }

	        String tempPassword = passwordGenerator.generate();

	        ZenUser user = new ZenUser();
	        user.setEmail(email);
	        user.setPassword(passwordEncoder.encode(tempPassword));
	        user.setFirstLogin(true);
	        user.setIsActive(true);

	        userRepository.save(user);
	        emailService.sendInvitationEmail(user.getEmail(), tempPassword);

	        return ResponseEntity.ok("Invitation sent successfully to " + user.getEmail());
	    } catch (Exception e) {
	        String errorMsg = "Failed to invite " + email + ": " + e.getMessage();
	        emailService.sendFailureReportToAdmin(List.of(errorMsg));
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
	    }
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
	    ZenUser user = userRepository.findByUsername(request.getUsername())
	        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password is incorrect");
	    }

	    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
	    user.setFirstLogin(false);
	    userRepository.save(user);

	    return ResponseEntity.ok("Password changed successfully");
	}


}

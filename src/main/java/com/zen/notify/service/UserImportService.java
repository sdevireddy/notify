package com.zen.notify.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zen.notify.controller.UserImportResponse;
import com.zen.notify.dto.UserImportDTO;
import com.zen.notify.entity.ZenUser;
import com.zen.notify.repository.UserRepository;
import com.zen.notify.utility.UserImportFailure;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserImportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Validator validator;

    public UserImportResponse importUsersFromCSV(MultipartFile file) {
        List<UserImportFailure> failed = new ArrayList<>();
        int successCount = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String header = reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] fields = line.split(",", -1); // handle empty strings

                    UserImportDTO dto = mapLineToDTO(fields);
                    Set<ConstraintViolation<UserImportDTO>> violations = validator.validate(dto);

                    if (!violations.isEmpty()) {
                        failed.add(new UserImportFailure(line, violations.iterator().next().getMessage()));
                        continue;
                    }

                    ZenUser entity = convertToEntity(dto);
                    userRepository.save(entity);
                    successCount++;

                } catch (Exception e) {
                    failed.add(new UserImportFailure(line, e.getMessage()));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to process file", e);
        }

        UserImportResponse response = new UserImportResponse();
        response.setTotalRecords(successCount + failed.size());
        response.setSuccessCount(successCount);
        response.setFailureCount(failed.size());
        response.setFailedRecords(failed);

        // send email
        if (!failed.isEmpty()) {
            sendFailureReport(failed);
        }

        return response;
    }

    private UserImportDTO mapLineToDTO(String[] fields) {
        UserImportDTO dto = new UserImportDTO();

        dto.setUsername(fields[0]);
        dto.setEmail(fields[1]);
        dto.setFirstName(fields[2]);
        dto.setLastName(fields[3]);
        dto.setPhoneNumber(fields[4]);
        dto.setIsActive(parseBooleanSafe(fields[5]));
        dto.setLastLogin(parseInstantSafe(fields[6]));
        dto.setCreatedAt(parseInstantSafe(fields[7]));
        dto.setUpdatedAt(parseInstantSafe(fields[8]));
        dto.setProfilePictureUrl(fields[9]);
        dto.setTimezone(fields[10]);
        dto.setLanguagePreference(fields[11]);
        dto.setDepartment(fields[12]);
        dto.setJobTitle(fields[13]);
        dto.setManagerId(parseLongSafe(fields[14]));
        dto.setAddress(fields[15]);
        dto.setDateOfBirth(parseLocalDateSafe(fields[16]));
        dto.setGender(fields[17]);
        dto.setAccountLocked(parseBooleanSafe(fields[18]));
        dto.setTwoFactorEnabled(parseBooleanSafe(fields[19]));
        dto.setSecurityQuestion(fields[20]);

        return dto;
    }
    
    
    private Boolean parseBooleanSafe(String value) {
        return (value != null && !value.isBlank()) ? Boolean.parseBoolean(value.trim()) : null;
    }

    private Long parseLongSafe(String value) {
        try {
            return (value != null && !value.isBlank()) ? Long.parseLong(value.trim()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Instant parseInstantSafe(String value) {
        try {
            return (value != null && !value.isBlank()) ? Instant.parse(value.trim()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate parseLocalDateSafe(String value) {
        try {
            return (value != null && !value.isBlank()) ? LocalDate.parse(value.trim()) : null;
        } catch (Exception e) {
            return null;
        }
    }



    private ZenUser convertToEntity(UserImportDTO dto) {
        ZenUser user = new ZenUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        return user;
    }

    private void sendFailureReport(List<UserImportFailure> failures) {
        StringBuilder content = new StringBuilder("Import Failures:\n\n");

        for (UserImportFailure fail : failures) {
            content.append("Line: ").append(fail.getRawLine()).append("\n");
            content.append("Error: ").append(fail.getErrorMessage()).append("\n\n");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("admin@example.com");
            message.setSubject("User Import Failures");
            message.setText(content.toString());
            mailSender.send(message);
        } catch (Exception e) {
            // log error
        }
    }
}


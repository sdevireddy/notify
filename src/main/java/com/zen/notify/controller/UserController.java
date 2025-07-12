package com.zen.notify.controller;

import java.security.SecureRandom;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.dto.UserDTO;
import com.zen.notify.email.EmailService;
import com.zen.notify.entity.ZenUser;
import com.zen.notify.service.UserService;

@RestController
@RequestMapping("/crm/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ZenUser> getUserById(@PathVariable Long id) {
        log.info("🔍 Fetching user by ID: {}", id);
        ZenUser user = userService.getUserById(id);
        log.debug("✅ Fetched user: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<ZenUser> updateUser(@PathVariable Long id, @RequestBody ZenUser user) {
        log.info("🛠️ Updating user with ID: {}", id);
        ZenUser updated = userService.updateUser(id, user);
        log.debug("✅ Updated user: {}", updated.getUsername());
        return ResponseEntity.ok(updated);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("🗑️ Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.info("✅ User deleted: {}", id);
        return ResponseEntity.noContent().build();
    }

    // Get all users (paginated)
    @GetMapping
    public ResponseEntity<PaginatedResponse<ZenUser>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        log.info("📄 Fetching paginated user list - Page: {}, Size: {}", page, pageSize);
        Page<ZenUser> userPage = userService.getUsersPaginated(page, pageSize);

        PaginatedResponse<ZenUser> response = new PaginatedResponse<>(
                userPage.getTotalElements(),
                userPage.getSize(),
                userPage.getNumber(),
                userPage.getTotalPages(),
                userPage.getContent()
        );

        log.info("✅ {} users retrieved", userPage.getNumberOfElements());
        return ResponseEntity.ok(response);
    }

    // Create user with email invite and password generation
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userCreateDTO) {
        log.info("🆕 Creating new user: {}", userCreateDTO.getUsername());
        UserDTO userDTO = new UserDTO();
        userDTO.setMessage("User Created Successfully");

        try {
            String generatedPassword = generateRandomPassword();
            log.debug("🔐 Generated password for {}: {}", userCreateDTO.getEmail(), generatedPassword);

            ZenUser user = mapToEntity(userCreateDTO);
            user.setPassword(passwordEncoder.encode(generatedPassword));

            ZenUser savedUser = userService.createUser(user);
            userDTO = mapToDTO(savedUser);

            emailService.sendAccountCreationEmail(savedUser.getEmail(), generatedPassword);
            log.info("✅ User created and email sent to: {}", savedUser.getEmail());

            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("❌ Failed to create user {}: {}", userCreateDTO.getEmail(), ex.getMessage(), ex);
            userDTO.setMessage("Failed to create user: " + ex.getMessage());
            return new ResponseEntity<>(userDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Password generator utility
    private String generateRandomPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    // Map DTO to Entity
    private ZenUser mapToEntity(UserDTO dto) {
        ZenUser user = new ZenUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setIsActive(dto.getIsActive());
        user.setLastLogin(dto.getLastLogin());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());
        user.setTimezone(dto.getTimezone());
        user.setLanguagePreference(dto.getLanguagePreference());
        user.setDepartment(dto.getDepartment());
        user.setJobTitle(dto.getJobTitle());
        user.setManagerId(dto.getManagerId());
        user.setAddress(dto.getAddress());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(dto.getGender());
        user.setAccountLocked(dto.getAccountLocked());
        user.setTwoFactorEnabled(dto.getTwoFactorEnabled());
        user.setSecurityQuestion(dto.getSecurityQuestion());
        return user;
    }

    // Map Entity to DTO
    private UserDTO mapToDTO(ZenUser user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setIsActive(user.getIsActive());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setTimezone(user.getTimezone());
        dto.setLanguagePreference(user.getLanguagePreference());
        dto.setDepartment(user.getDepartment());
        dto.setJobTitle(user.getJobTitle());
        dto.setManagerId(user.getManagerId());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setGender(user.getGender());
        dto.setAccountLocked(user.getAccountLocked());
        dto.setTwoFactorEnabled(user.getTwoFactorEnabled());
        dto.setSecurityQuestion(user.getSecurityQuestion());
        return dto;
    }
}

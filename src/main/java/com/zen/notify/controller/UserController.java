package com.zen.notify.controller;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.dto.UserDTO;
import com.zen.notify.email.EmailService;
import com.zen.notify.entity.ZenUser;
import com.zen.notify.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ZenUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Create a new user
	/*
	 * @PostMapping("/create") public ResponseEntity<?> createUser(@RequestBody
	 * ZenUser user) { try { ZenUser createdUser = userService.createUser(user);
	 * return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); } catch
	 * (IllegalArgumentException ex) { return
	 * ResponseEntity.badRequest().body(ex.getMessage()); } catch (Exception ex) {
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body("Failed to create user"); } }
	 */

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<ZenUser> updateUser(@PathVariable Long id, @RequestBody ZenUser user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
	/*
	 * // Get all users
	 * 
	 * @GetMapping public List<User> getAllUsers() { return
	 * userService.getAllUsers(); }
	 */
    
    @GetMapping
    public ResponseEntity<PaginatedResponse<ZenUser>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<ZenUser> userPage = userService.getUsersPaginated(page, pageSize);

        PaginatedResponse<ZenUser> response = new PaginatedResponse<>(
        		userPage.getTotalElements(),
        		userPage.getSize(),
        		userPage.getNumber(),
        		userPage.getTotalPages(),
        		userPage.getContent()
        );


        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userCreateDTO) {
    	 UserDTO userDTO = new UserDTO();
    	 userDTO.setMessage("User Created Successfully");
    	try {
    		 String generatedPassword = generateRandomPassword();
    	        System.out.println("password is - " + generatedPassword);
    	        ZenUser user = mapToEntity(userCreateDTO);
    	        user.setPassword(passwordEncoder.encode(generatedPassword));
    	        ZenUser savedUser = userService.createUser(user);
    	        userDTO.setMessage("User Created Successfully");
    	        userDTO = mapToDTO(savedUser);
    	        //emailService.sendAccountCreationEmail(savedUser.getEmail(), generatedPassword);
    	        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    	}catch(Exception aEx) {
    		 userDTO.setMessage(aEx.getMessage());
    		 aEx.printStackTrace();
    		 return new ResponseEntity<>(userDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
       
    }



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


    private ZenUser mapToEntity(UserDTO dto) {
        ZenUser user = new ZenUser();
        user.setId(dto.getId()); 
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


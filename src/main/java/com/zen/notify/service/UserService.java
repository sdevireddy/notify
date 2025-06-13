package com.zen.notify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zen.notify.entity.ZenUser;
import com.zen.notify.exceptions.UserNotFoundException;
import com.zen.notify.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ZenUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public ZenUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<ZenUser> getAllUsers() {
        return userRepository.findAll();
    }

    public ZenUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public ZenUser createUser(ZenUser user) {
        // Basic validation for mandatory fields
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("PasswordHash is required");
        }

        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

		/*
		 * // Set createdAt automatically via @PrePersist in entity String
		 * hashedPassword = passwordEncoder.encode(user.getPassword());
		 * user.setPassword(hashedPassword);
		 */

        return userRepository.save(user);
    }

    public ZenUser updateUser(Long id, ZenUser userDetails) {
        ZenUser user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        //user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public Page<ZenUser> getUsersPaginated(int page, int pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        return userRepository.findAll(pageable);
    }

}

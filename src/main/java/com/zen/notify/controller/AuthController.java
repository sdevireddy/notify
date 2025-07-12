package com.zen.notify.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.AuthResponse;
import com.zen.notify.filters.ZenUserDetails;
import com.zen.notify.service.ZenUserDetailsService;
import com.zen.notify.utility.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/crm")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ZenUserDetailsService zenUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> createToken(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("Attempting authentication for user: {}", authRequest.getUsername());

        try {
            // Uncomment if using AuthenticationManager
            // authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            throw new Exception("Invalid username or password", e);
        }

        ZenUserDetails userDetails;
        try {
            userDetails = zenUserDetailsService.loadUserByUsername(authRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            log.error("User not found: {}", authRequest.getUsername(), e);
            throw new Exception("User not found", e);
        }

        AuthResponse response = new AuthResponse();
        response.setUsername(authRequest.getUsername());
        response.setRoles(new ArrayList<>());
        response.setAccess_token(jwtUtil.generateToken(authRequest.getUsername()));
        response.setRefresh_token(jwtUtil.generateRefreshToken(userDetails));
        response.setExpires_in(1800); // seconds

        log.info("Authentication successful for user: {}", authRequest.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        log.info("Login request received for user: {}", request.getUsername());

        ZenUserDetails userDetails;
        try {
            userDetails = (ZenUserDetails) zenUserDetailsService.loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            log.error("Login failed. User not found: {}", request.getUsername(), e);
            return ResponseEntity.status(401).body("User not found");
        }

        String accessToken = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshCookie);

        Cookie tokenCookie = new Cookie("access_token", accessToken);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(tokenCookie);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccess_token(accessToken);
        authResponse.setUsername(userDetails.getUsername());
        authResponse.setRoles(userDetails.getRoleNames());
        authResponse.setModules(userDetails.getModules());

        log.info("Login successful for user: {}", request.getUsername());

        return ResponseEntity.ok(authResponse);
    }
}


class AuthRequest {
    private String username;
    private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
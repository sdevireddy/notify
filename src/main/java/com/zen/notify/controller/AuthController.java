package com.zen.notify.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.AuthResponse;
import com.zen.notify.filters.ZenUserDetails;
import com.zen.notify.service.ZenUserDetailsService;
import com.zen.notify.utility.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ZenUserDetailsService zenUserDetailsService;
    
    
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> createToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
        	System.out.println("username is " + authRequest.getUsername() 
        	+ "password is " + authRequest.getPassword());;
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
        	e.printStackTrace();
            throw new Exception("Invalid username or password", e);
        }
        
        ZenUserDetails userDetails = null;
		try {
			userDetails = zenUserDetailsService.loadUserByUsername(authRequest.getUsername());
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        AuthResponse response = new AuthResponse();
        response.setUsername(authRequest.getUsername());
        response.setRoles(new ArrayList());
        response.setAccess_token(jwtUtil.generateToken(authRequest.getUsername()));
        response.setRefresh_token(jwtUtil.generateRefreshToken(userDetails));
        response.setExpires_in(1800); // seconds

        return ResponseEntity.ok(response);
        
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



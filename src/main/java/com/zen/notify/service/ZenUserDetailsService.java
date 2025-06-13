package com.zen.notify.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zen.notify.entity.ZenUser;
import com.zen.notify.filters.ZenUserDetails;
import com.zen.notify.repository.UserRepository;

@Service
public class ZenUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ZenUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ZenUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User name is " + username);
    	ZenUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        System.out.println("username is " + user.getUsername());
        System.out.println("Password is " + user.getPassword());
    	if (user == null) {
        	  user = userRepository.findByEmail(username)
        	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        }
        return new ZenUserDetails(user);
    }
}



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
        ZenUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new ZenUserDetails(user);
    }
}



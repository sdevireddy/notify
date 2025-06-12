package com.zen.notify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.ZenUser;

@Repository
public interface UserRepository extends JpaRepository<ZenUser, Long> {
    
    Optional<ZenUser> findByUsername(String username);
    
    Optional<ZenUser> findByEmail(String email);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}


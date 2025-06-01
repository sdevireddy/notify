package com.zen.notify.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.Contact;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByAccount_AccountId(Long accountId);
    Optional findById(Long id);
}


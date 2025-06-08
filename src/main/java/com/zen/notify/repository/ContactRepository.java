package com.zen.notify.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Lead;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
    List<Contact> findByAccount_AccountId(Long accountId);
    Optional findById(Long id);
}


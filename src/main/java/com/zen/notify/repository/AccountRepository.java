package com.zen.notify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.Account;
import com.zen.notify.entity.Lead;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
	Optional<Account> findByAccountName(String accountName);
	Optional<Account> findById(Long id);
}


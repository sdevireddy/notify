package com.zen.notify.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Lead;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long>, JpaSpecificationExecutor<Deal> {
	  List<Deal> findByAccount_AccountId(Long accountId);
	    List<Deal> findByContact_ContactId(Long contactId);
}


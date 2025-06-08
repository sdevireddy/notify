package com.zen.notify.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Lead;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long>, JpaSpecificationExecutor<Deal> {
	
}

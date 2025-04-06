package com.zen.notify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.zen.notify.entity.Lead;

import java.util.Optional;

public interface LeadRepository extends JpaRepository<Lead, Long> {
	Lead findByEmail(String email);

	boolean existsByEmail(String email);
	
	 Optional<Lead> findByIdAndConvertedFalse(Long id);
	

}

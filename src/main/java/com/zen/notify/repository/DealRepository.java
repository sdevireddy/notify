package com.zen.notify.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zen.notify.entity.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
}

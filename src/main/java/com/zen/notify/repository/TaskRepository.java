package com.zen.notify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zen.notify.entity.Task;

interface TaskRepository extends JpaRepository<Task, Long> {} 


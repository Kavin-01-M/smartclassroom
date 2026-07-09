package com.example.smartclassroom.repository;

import com.example.smartclassroom.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
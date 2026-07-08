package com.example.smartclassroom.repository;

import com.example.smartclassroom.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
package com.example.smartclassroom.repository;

import com.example.smartclassroom.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
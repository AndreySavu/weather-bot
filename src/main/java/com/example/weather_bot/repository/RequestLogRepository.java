package com.example.weather_bot.repository;

import com.example.weather_bot.model.RequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
    Page<RequestLog> findByRequestTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<RequestLog> findByUserIdAndRequestTimeBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}
package com.example.weather_bot.repository;

import com.example.weather_bot.model.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestLogRepository extends JpaRepository<LogEntity, Long> {
    List<LogEntity> findByUserId(Long userId);
    List<LogEntity> findByRequestTimeBetween(LocalDateTime start, LocalDateTime end);
}
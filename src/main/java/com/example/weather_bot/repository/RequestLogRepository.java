package com.example.weather_bot.repository;

import com.example.weather_bot.model.RequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    @Query("""
            SELECT r FROM RequestLog r
            WHERE requestTime BETWEEN :startTime AND :endTime
            """)
    Page<RequestLog> findByRequestTimeBetween(@Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime,
                                              Pageable pageable);

    @Query("""
            SELECT r FROM RequestLog r
            WHERE userId = :userId
            AND requestTime BETWEEN :startTime AND :endTime
            """)
    Page<RequestLog> findByUserIdAndRequestTimeBetween(@Param("userId") Long userId,
                                                       @Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime,
                                                       Pageable pageable);

    @Query("""
            SELECT r FROM RequestLog r
            WHERE userId = :userId
            """)
    Page<RequestLog> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
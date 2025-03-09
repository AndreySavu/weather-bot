package com.example.weather_bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RequestLogRepository extends JpaRepository<RequestLogEntity, Long> {

    @Query("""
            SELECT r FROM RequestLogEntity r
            WHERE requestTime BETWEEN :startTime AND :endTime
            """)
    Page<RequestLogEntity> findByRequestTimeBetween(@Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime,
                                                    Pageable pageable);

    @Query("""
            SELECT r FROM RequestLogEntity r
            WHERE userId = :userId
            AND requestTime BETWEEN :startTime AND :endTime
            """)
    Page<RequestLogEntity> findByUserIdAndRequestTimeBetween(@Param("userId") Long userId,
                                                             @Param("startTime") LocalDateTime startTime,
                                                             @Param("endTime") LocalDateTime endTime,
                                                             Pageable pageable);

    @Query("""
            SELECT r FROM RequestLogEntity r
            WHERE userId = :userId
            """)
    Page<RequestLogEntity> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
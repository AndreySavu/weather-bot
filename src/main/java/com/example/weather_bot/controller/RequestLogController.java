package com.example.weather_bot.controller;

import com.example.weather_bot.model.RequestLog;
import com.example.weather_bot.service.RequestLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "Логи запросов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class RequestLogController {

    private final RequestLogService requestLogService;

    @Operation(summary = "Получение логов",
            description = "Получение логов по запросам с пагинацией и фильтром по времени")
    @GetMapping
    public ResponseEntity<Page<RequestLog>> getLogs(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime
    ) {
        Pageable pageable = PageRequest.of(offset, limit);
        LocalDateTime startTimeParsed = startTime != null ? LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME) : null;

        LocalDateTime endTimeParsed = endTime != null ? LocalDateTime.parse(endTime, DateTimeFormatter.ISO_DATE_TIME) : null;

        Page<RequestLog> logs = requestLogService.getRequestLogs(startTimeParsed, endTimeParsed, pageable);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Получение логов по конкретному пользователю",
            description = "Получение логов по конкретному пользователю с пагинацией и фильтром по времени")
    @GetMapping("/{userId}")
    public ResponseEntity<Page<RequestLog>> getLogsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime
    ) {
        Pageable pageable = PageRequest.of(offset, limit);
        LocalDateTime startTimeParsed = startTime != null ? LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME) : null;
        LocalDateTime endTimeParsed = endTime != null ? LocalDateTime.parse(endTime, DateTimeFormatter.ISO_DATE_TIME) : null;

        Page<RequestLog> logs = requestLogService.getRequestLogsByUserId(userId, startTimeParsed, endTimeParsed, pageable);
        return ResponseEntity.ok(logs);
    }
}
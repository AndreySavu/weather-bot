package com.example.weather_bot.controller;

import com.example.weather_bot.mapper.MapStructMapper;
import com.example.weather_bot.repository.RequestLogEntity;
import com.example.weather_bot.service.RequestLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Логи запросов")
@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class RequestLogController {

    private final RequestLogService requestLogService;
    private final MapStructMapper mapper;

    @Operation(summary = "Получение логов",
            description = """
                    Получение логов по запросам с пагинацией и фильтром по времени.
                    Время вводится в формате yyyy-MM-dd HH:mm""")
    @GetMapping
    public ResponseEntity<Page<RequestLogResponse>> getLogs(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime
    ) {
        Page<RequestLogResponse> logs = mapper.toRequestLogResponse(
                requestLogService.getRequestLogs(startTime, endTime, offset, limit));
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Получение логов по конкретному пользователю",
            description = """
                    Получение логов по конкретному пользователю с пагинацией и фильтром по времени.
                    Время вводится в формате yyyy-MM-dd HH:mm""")
    @GetMapping("/{userId}")
    public ResponseEntity<Page<RequestLogResponse>> getLogsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime
    ) {
        Page<RequestLogResponse> logs = mapper.toRequestLogResponse(
                requestLogService.getRequestLogsByUserId(userId, startTime, endTime, offset, limit));
        return ResponseEntity.ok(logs);
    }
}
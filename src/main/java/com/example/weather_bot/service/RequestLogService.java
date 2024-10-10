package com.example.weather_bot.service;

import com.example.weather_bot.model.RequestLog;
import com.example.weather_bot.repository.RequestLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    public void createRequestLog(RequestLog requestLog) {
        requestLogRepository.save(requestLog);
    }

    public Page<RequestLog> getRequestLogs(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        if (startTime != null && endTime != null) {
            return requestLogRepository.findByRequestTimeBetween(startTime, endTime, pageable);
        } else {
            return requestLogRepository.findAll(pageable);
        }
    }

    public Page<RequestLog> getRequestLogsByUserId(Long userId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return requestLogRepository.findByUserIdAndRequestTimeBetween(userId, startTime, endTime, pageable);
    }
}

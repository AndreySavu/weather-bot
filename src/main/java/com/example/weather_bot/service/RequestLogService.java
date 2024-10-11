package com.example.weather_bot.service;

import com.example.weather_bot.model.RequestLog;
import com.example.weather_bot.repository.RequestLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    public void createRequestLog(RequestLog requestLog) {
        requestLogRepository.save(requestLog);
    }

    public Page<RequestLog> getRequestLogs(String startTime, String endTime, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (startTime != null && endTime != null) {
            LocalDateTime startTimeParsed = LocalDateTime.parse(startTime, formatter);
            LocalDateTime endTimeParsed = LocalDateTime.parse(endTime, formatter);
            return requestLogRepository.findByRequestTimeBetween(startTimeParsed, endTimeParsed, pageable);
        } else {
            return requestLogRepository.findAll(pageable);
        }
    }

    public Page<RequestLog> getRequestLogsByUserId(Long userId, String startTime, String endTime, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (startTime != null && endTime != null) {
            LocalDateTime startTimeParsed = LocalDateTime.parse(startTime, formatter);
            LocalDateTime endTimeParsed = LocalDateTime.parse(endTime, formatter);
            return requestLogRepository.findByUserIdAndRequestTimeBetween(userId,startTimeParsed, endTimeParsed, pageable);
        } else {
            return requestLogRepository.findByUserId(userId, pageable);
        }
    }
}

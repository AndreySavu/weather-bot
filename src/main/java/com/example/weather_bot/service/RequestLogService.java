package com.example.weather_bot.service;

import com.example.weather_bot.mapper.MapStructMapper;
import com.example.weather_bot.repository.RequestLogEntity;
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
    private final MapStructMapper mapper;

    public void createRequestLog(RequestLog requestLog) {
        requestLogRepository.save(mapper.toRequestLogEntity(requestLog));
    }

    public Page<RequestLog> getRequestLogs(String startTime, String endTime, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (startTime != null && endTime != null) {
            LocalDateTime startTimeParsed = LocalDateTime.parse(startTime, formatter);
            LocalDateTime endTimeParsed = LocalDateTime.parse(endTime, formatter);
            return mapper.fromRequestLogEntity(
                    requestLogRepository.findByRequestTimeBetween(startTimeParsed, endTimeParsed, pageable));
        } else {
            return mapper.fromRequestLogEntity(requestLogRepository.findAll(pageable));
        }
    }

    public Page<RequestLog> getRequestLogsByUserId(Long userId, String startTime, String endTime, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (startTime != null && endTime != null) {
            LocalDateTime startTimeParsed = LocalDateTime.parse(startTime, formatter);
            LocalDateTime endTimeParsed = LocalDateTime.parse(endTime, formatter);
            return mapper.fromRequestLogEntity(
                    requestLogRepository.findByUserIdAndRequestTimeBetween(userId,startTimeParsed, endTimeParsed, pageable));
        } else {
            return mapper.fromRequestLogEntity(requestLogRepository.findByUserId(userId, pageable));
        }
    }
}

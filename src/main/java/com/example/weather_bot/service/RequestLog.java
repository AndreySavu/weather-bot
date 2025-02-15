package com.example.weather_bot.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
public class RequestLog {
    private Long id;
    private String command;
    private LocalDateTime requestTime;
    private String response;
    private long userId;
}

package com.example.weather_bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_logs")

public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String command;
    private LocalDateTime requestTime;
    private String response;

    public RequestLog(Long userId, String command, LocalDateTime requestTime, String response){
        this.userId = userId;
        this.command = command;
        this.requestTime = requestTime;
        this.response = response;
    }

}
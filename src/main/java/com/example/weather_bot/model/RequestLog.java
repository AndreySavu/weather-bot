package com.example.weather_bot.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "request_logs")
@Schema(description = "Модель для логирования запросов")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор записи", example = "1")
    private Long id;

    @Schema(description = "Команда, которую пользователь отправил", example = "/weather Новосибирск")
    private String command;

    @Column(name = "request_time")
    @Schema(description = "Время, когда был получен запрос", example = "2024-10-10T12:00:00")
    private LocalDateTime requestTime;

    @Schema(description = "Ответ, который был отправлен пользователю", example = "Температура: ....")
    private String response;

    @Column(name = "user_id")
    @Schema(description = "Идентификатор пользователя, который отправил запрос", example = "778836599")
    private Long userId;

    public RequestLog(Long userId, String command, LocalDateTime requestTime, String response) {
        this.userId = userId;
        this.command = command;
        this.requestTime = requestTime;
        this.response = response;
    }

    public RequestLog() {
    }
}
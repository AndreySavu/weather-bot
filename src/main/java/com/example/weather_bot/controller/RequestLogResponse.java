package com.example.weather_bot.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Логи запросов")
public record RequestLogResponse(
        @Schema(description = "Уникальный идентификатор записи", example = "1")
        Long id,
        @Schema(description = "Команда, которую пользователь отправил", example = "/weather Новосибирск")
        String command,
        @Schema(description = "Время, когда был получен запрос", example = "2024-10-10T12:00:00")
        LocalDateTime requestTime,
        @Schema(description = "Ответ, который был отправлен пользователю", example = "Температура: ....")
        String response,
        @Schema(description = "Идентификатор пользователя, который отправил запрос", example = "778836599")
        long userId
) {
}

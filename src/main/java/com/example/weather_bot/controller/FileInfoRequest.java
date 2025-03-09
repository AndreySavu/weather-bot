package com.example.weather_bot.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о загружаемом файле (картинке)")
public record FileInfoRequest(
        @Schema(description = "Название")
        String name,
        @Schema(description = "Тип погоды")
        String weatherType,
        @Schema(description = "Степень по шкале от 0 до 10")
        int rate
) {
}

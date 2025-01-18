package com.example.weather_bot.telegram.responses;

public record Weather(
        int id,
        String main,
        String description // Описание погоды
) {
}
package com.example.weather_bot.telegram.responses;

public record Wind(
        double speed,  // Скорость ветра
        int deg
) {
}

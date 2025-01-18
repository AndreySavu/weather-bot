package com.example.weather_bot.telegram.responses;

public record Main(
        double temp,        // Температура
        double feels_like,  // Ощущаемая температура
        int humidity,       // Влажность
        double pressure
) {
}
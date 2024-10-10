package com.example.weather_bot.model;

import lombok.Data;

@Data
public class WeatherResponse {
    private Main main;
    private String weather;
    // Другие поля...

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private int humidity;
    }
}

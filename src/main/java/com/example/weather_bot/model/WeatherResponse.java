package com.example.weather_bot.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;
@Getter
@Data
public class WeatherResponse {
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private String name;
    private String cod;

    @Data
    public static class Weather {
        private int id;
        private String main;
        private String description; // Описание погоды

    }

    @Data
    public static class Main {
        private double temp;        // Температура
        private double feels_like;  // Ощущаемая температура
        private int humidity;       // Влажность
        private double pressure;
    }

    @Data
    public static class Wind {
        private double speed;  // Скорость ветра
        private int deg;
    }
}

package com.example.weather_bot.telegram.responses;

import lombok.Data;
import lombok.Getter;

import java.util.List;

public record WeatherResponse(
        List<Weather> weather,
        Main main,
        Wind wind,
        String name,
        String cod
) {




}

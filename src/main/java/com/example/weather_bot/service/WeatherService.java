package com.example.weather_bot.service;

import com.example.weather_bot.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String weatherApiKey;
    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<WeatherResponse> getWeather(String city) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=ru", city, weatherApiKey);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
}

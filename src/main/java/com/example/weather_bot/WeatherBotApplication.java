package com.example.weather_bot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class WeatherBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherBotApplication.class, args);
	}

}

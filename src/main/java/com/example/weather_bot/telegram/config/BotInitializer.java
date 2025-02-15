package com.example.weather_bot.telegram.config;

import com.example.weather_bot.telegram.service.WeatherBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {
    private final WeatherBot weatherBot;
    @Autowired
    public BotInitializer(WeatherBot weatherBot) {
        this.weatherBot = weatherBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init()throws TelegramApiException{
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try{
            telegramBotsApi.registerBot(weatherBot);
        } catch (TelegramApiException e){

        }
    }
}

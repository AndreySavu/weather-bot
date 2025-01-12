package com.example.weather_bot.service;

import com.example.weather_bot.config.BotConfig;
import com.example.weather_bot.model.RequestLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class WeatherBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final WeatherService weatherService;
    private final RequestLogService requestLogService;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            }
            if (messageText.startsWith("/weather")) {
                String city = messageText.substring(9).trim();
                if (city.isEmpty()) {
                    sendMessage(chatId, "Пожалуйста, укажите город.");
                } else {
                    weatherService.getWeather(city)
                            .subscribe(weatherResponse -> {
                                String response = String.format("""
                                                Температура: %.1f°C
                                                Ощущается как: %.1f°C
                                                Описание: %s
                                                Влажность: %d%%
                                                Скорость ветра: %sм/с""",
                                                weatherResponse.getMain().getTemp(),
                                                weatherResponse.getMain().getFeels_like(),
                                                weatherResponse.getWeather().get(0).getDescription(),
                                                weatherResponse.getMain().getHumidity(),
                                                weatherResponse.getWind().getSpeed());
                                sendMessage(chatId, response);

                                // Логирование
                                RequestLog log = new RequestLog(userId, messageText, LocalDateTime.now(), response);
                                requestLogService.createRequestLog(log);
                            }, throwable -> {
                                sendMessage(chatId, "Не удалось получить данные о погоде для этого города.");
                            });
                }
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Добро пожаловать, " + name + "!\n" +
                "Это бот для получения погоды." + "\n" +
                "Для получения погоды введите команду /weather <город>";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Вывод ошибки в консоль (желательно реализовать в более удобном формате)
        }
    }

}

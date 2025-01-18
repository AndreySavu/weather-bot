# Backend-сервис, взаимодействующий с Telegram Bot API

Тестовое задание для BobrAi.

## Включает в себя:

1. **Telegram-бот**, доступный по тегу @weather_34523_bot (https://t.me/weather_34523_bot).
2. **REST API** для работы с историей запросов, доступный по адресу http://localhost:8080/webjars/swagger-ui/index.html#/.

## Инструкция по запуску

Для запуска приложения выполните следующие шаги:

1. Убедитесь, что у вас установлен Java 17 и Maven.
2. Склонируйте репозиторий на свою локальную машину:
   ```.bash
   git clone https://github.com/andreysavu/weather-bot.git
   cd weather-bot
   ```
3. Создайте экземпляр базы данных PostgreSQL 'weather_logs' на порту 5432, логин и пароль: postgres.
   Или настройте подключения к базе данных в файле `application.properties`, используя следующие параметры:
   
 ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password
 ```
   
4. Запустите приложение с помощью Maven:
   
```.bash
   mvn spring-boot:run
```

## Документация

1. **Telegram-бот**:
    Команды:
- /start - вывод приветственного сообщения
- /weather <город> - вывод данных о погоде в определенном городе
  
2. **REST API для работы с историей запросов**:
- Документация приведена в Swagger'e, доступном по адресу http://localhost:8080/webjars/swagger-ui/index.html#/.

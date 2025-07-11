package com.example.weather_report;

import com.example.weather_report.enums.City;
import com.example.weather_report.enums.WeatherCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherProducer {

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    // Продюсер сообщений о погоде каждые 5 секунд
    @Scheduled(fixedRate = 5000)
     void sendWeather() {
        try {
            String json = objectMapper.writeValueAsString(generateRandomWeatherData());
            kafka.send("weather", json);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Генерация случайных данных о погоде
    private WeatherData generateRandomWeatherData() {
        WeatherData data = new WeatherData();
        data.setTemperature(random.nextInt(36));
        data.setCondition(randomEnum(WeatherCondition.class));
        data.setCity(randomEnum(City.class));
        LocalDate randomDate = LocalDate.ofEpochDay(
                ThreadLocalRandom.current().nextLong(
                        LocalDate.of(2025, 7, 1).toEpochDay(),
                        LocalDate.of(2025, 7, 31).toEpochDay() + 1
                )
        );
        data.setDate(randomDate);
        return data;
    }

    // Генерация случайного перечисления
    private <T extends Enum<?>> T randomEnum(Class<T> type) {
        T[] types = type.getEnumConstants();
        return types[random.nextInt(types.length)];
    }


}

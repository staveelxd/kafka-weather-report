package com.example.weather_report;

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

    @Scheduled(fixedRate = 5000)
     void sendWeather() {
        try {
            WeatherData data = new WeatherData();
            data.setTemperature(random.nextInt(36));
            data.setCondition(randomEnum(WeatherCondition.class));
            data.setCity(randomEnum(City.class));
            LocalDate randomDate = LocalDate.ofEpochDay(
                    ThreadLocalRandom.current().nextLong(
                            LocalDate.of(2025, 1, 1).toEpochDay(),
                            LocalDate.of(2025, 12, 31).toEpochDay() + 1
                    )
            );
            data.setDate(randomDate);
            String json = objectMapper.writeValueAsString(data);
            kafka.send("weather", json);
            log.info("Отправлено сообщение: {}", json);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private <T extends Enum<?>> T randomEnum(Class<T> type) {
        T[] types = type.getEnumConstants();
        return types[random.nextInt(types.length)];
    }
}

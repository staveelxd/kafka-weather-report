package com.example.weather_report;

import com.example.weather_report.enums.WeatherCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class WeatherConsumer {

    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(WeatherConsumer.class);
    private final WeatherStats stats = new WeatherStats();

    public WeatherConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Консюмер сообщений о погоде
    @KafkaListener(topics = "weather")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            WeatherData data = objectMapper.readValue(record.value(), WeatherData.class);
            processWeatherStats(data);
            log.info(data.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // Обработка данных о погоде
    private void processWeatherStats(WeatherData data) {
        if (stats.getHottestDay() == null || data.getTemperature() > stats.getHottestDay().getTemperature()) {
            stats.setHottestDay(data);
        }
        if (stats.getColdestDay() == null || data.getTemperature() < stats.getColdestDay().getTemperature()) {
            stats.setColdestDay(data);
        }
        if (data.getCondition() == WeatherCondition.дождь) {
            stats.getRainCount().merge(String.valueOf(data.getCity()), 1, Integer::sum);
        }
    }

    // Анализ данных о погоде каждую минуту
    @Scheduled(cron = "0 * * * * *")
    private void analysis() {
        log.info(stats.toString());
    }
}

package com.example.weather_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WeatherConsumer {

    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(WeatherConsumer.class);

    public WeatherConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "weather")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            WeatherData data = objectMapper.readValue(record.value(), WeatherData.class);
            log.info(data.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}


package com.example.weather_report;

import com.example.weather_report.data.WeatherData;
import com.example.weather_report.data.WeatherStats;
import com.example.weather_report.enums.City;
import com.example.weather_report.enums.WeatherCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeatherConsumerTest {

    private WeatherConsumer consumer;
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        consumer = new WeatherConsumer(mapper);
    }

    @Test
    void testListenAndAggregateStats() throws Exception {
        WeatherData data = new WeatherData();
        data.setCity(City.Москва);
        data.setCondition(WeatherCondition.дождь);
        data.setTemperature(30);
        data.setDate(LocalDate.of(2025, 7, 10));

        String json = mapper.writeValueAsString(data);

        ConsumerRecord<String, String> record = new ConsumerRecord<>("weather", 0, 0L, null, json);

        consumer.listen(record);

        WeatherStats stats = consumer.getStats();

        assertNotNull(stats.getHottestDay());
        assertEquals(30, stats.getHottestDay().getTemperature());
        assertEquals(City.Москва, stats.getHottestDay().getCity());

        assertEquals(1, stats.getRainCount().get("Москва"));
    }
}

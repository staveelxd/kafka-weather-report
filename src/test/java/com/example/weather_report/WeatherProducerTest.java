package com.example.weather_report;

import com.example.weather_report.data.WeatherData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class WeatherProducerTest {

	private final KafkaTemplate<String, String> kafka = Mockito.mock(KafkaTemplate.class);
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	private final WeatherProducer producer = new WeatherProducer(kafka, mapper);

	@Test
	void testGenerateRandomWeatherData() {
		WeatherData data = producer.generateRandomWeatherData();
		assertNotNull(data.getCity());
		assertNotNull(data.getCondition());
		assertNotNull(data.getDate());
		assertTrue(data.getTemperature() >= 0 && data.getTemperature() <= 35);
	}

	@Test
	void testSendWeather() {
		assertDoesNotThrow(producer::sendWeather);
		verify(kafka).send(any(), any());
	}
}
package com.example.weather_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherApp {
	public static void main(String[] args) {
		SpringApplication.run(WeatherApp.class, args);
	}
}

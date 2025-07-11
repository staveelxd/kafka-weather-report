package com.example.weather_report;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherData {
    private int temperature;
    private WeatherCondition condition;
    private City city;
    private LocalDate date;

    @Override
    public String toString() {
        return "Получена погода\n" +
                "температура: " + temperature +
                "\nсостояние: " + condition +
                "\nгород: " + city +
                "\nдата:" + date;
    }
}

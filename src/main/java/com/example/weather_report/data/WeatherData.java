package com.example.weather_report.data;

import com.example.weather_report.enums.City;
import com.example.weather_report.enums.WeatherCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private int temperature;
    private WeatherCondition condition;
    private City city;
    private LocalDate date;

    @Override
    public String toString() {
        return  "Погода:\n" +
                "Температура: " + temperature +
                "\nСостояние: " + condition +
                "\nГород: " + city +
                "\nДата:" + date;
    }
}

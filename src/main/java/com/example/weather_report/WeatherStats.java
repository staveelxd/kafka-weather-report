package com.example.weather_report;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class WeatherStats {
    private final Map<String, Integer> rainCount = new HashMap<>();
    private WeatherData hottestDay;
    private WeatherData coldestDay;

    @Override
    public String toString() {
        if (hottestDay == null || coldestDay == null || rainCount.isEmpty()) {
            return "Статистика о погоде пока недоступна.";
        }

        String cityWithMostRain = rainCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Неизвестно");

        int maxRainDays = rainCount.getOrDefault(cityWithMostRain, 0);

        return "===============Статистика о погоде================\n" +
                String.format("Самый жаркий день: %s в городе %s, температура = %d°C\n",
                        hottestDay.getDate(), hottestDay.getCity(), hottestDay.getTemperature()) +
                String.format("Самый холодный день: %s в городе %s, температура = %d°C\n",
                        coldestDay.getDate(), coldestDay.getCity(), coldestDay.getTemperature()) +
                String.format("Город с наибольшим количеством дождливых дней: %s, количество дождливых дней = %d",
                        cityWithMostRain, maxRainDays);
    }

}

package org.nilhahn.weather.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// https://openweathermap.org/current#current_JSON
// https://openweathermap.org/weather-conditions
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherResponse {
    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private TempratureAndHumidity main;
    private Long visibility;
    private Wind wind;
    private Clouds clouds;
    private String dt; // Time of data calculation, unix, UTC
    private SystemInfo sys;
    private Long timezone;
    private Long id;    // City Id
    private String name;    // City name
    private Long cod;
}

package org.nilhahn.weather.storage.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
    private Long id;
    private String cityName;
    private String weatherId;
    private String weatherDescription;
    private String weatherMain;
    private String weatherIcon;
    private String temp;
    private String tempMin;
    private String tempMax;
    private String feelsLike;
    private String humidity;
    private String pressure;
    private String unixTime;
}

package org.nilhahn.weather.storage.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WeatherData {
    private Long id;
    private String cityName;
    private String weatherId;
    private String weatherDescription;
    private String weatherMain;
    private String weatherIcon;

}

package org.nilhahn.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.model.TempratureAndHumidity;
import org.nilhahn.weather.model.Weather;
import org.nilhahn.weather.model.WeatherResponse;
import org.nilhahn.weather.storage.Storage;
import org.nilhahn.weather.storage.StorageFactory;
import org.nilhahn.weather.storage.data.WeatherData;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class StorageService {

    private final Storage storage;

    public StorageService() {
        this.storage = StorageFactory.getInstance()
                .orElseThrow();
    }

    public void store(WeatherResponse weather) {
        try {
            this.storage.write(this.toWeatherData(weather));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public WeatherResponse read(String location) throws IOException {
        WeatherData data = this.storage.read(location);
        return this.fromWeatherData(data);
    }

    public List<WeatherResponse> readBulk(String location) throws IOException {
        List<WeatherData> data = this.storage.readAll(location);
        List<WeatherResponse> result = new ArrayList<>();
        data.forEach(element -> result.add(this.fromWeatherData(element)));
        return result;
    }

    private WeatherData toWeatherData(WeatherResponse weather) {
        Weather weatherData = weather.getWeather().get(0);
        return WeatherData.builder()
                .id(weather.getId())
                .cityName(weather.getName())
                .weatherId(String.valueOf(weatherData.getId()))
                .weatherDescription(weatherData.getDescription())
                .weatherMain(weatherData.getMain())
                .weatherIcon(weatherData.getIcon())
                .temp(weather.getMain().getTemp().toString())
                .tempMin(weather.getMain().getTemp_min().toString())
                .tempMax(weather.getMain().getTemp_max().toString())
                .feelsLike(weather.getMain().getFeels_like().toString())
                .humidity(weather.getMain().getHumidity().toString())
                .pressure(weather.getMain().getPressure().toString())
                .unixTime(weather.getDt())
                .build();
    }

    private WeatherResponse fromWeatherData(WeatherData data) {
        return WeatherResponse.builder()
                .id(data.getId())
                .clouds(null)
                .coord(null)
                .sys(null)
                .wind(null)
                .weather(Collections.singletonList(
                        Weather.builder()
                                .id(Long.valueOf(data.getWeatherId()))
                                .description(data.getWeatherDescription())
                                .main(data.getWeatherMain())
                                .icon(data.getWeatherIcon()).build()
                ))
                .main(TempratureAndHumidity.builder()
                        .temp(new BigDecimal(data.getTemp()))
                        .temp_max(new BigDecimal(data.getTempMax()))
                        .temp_min(new BigDecimal(data.getTempMin()))
                        .feels_like(new BigDecimal(data.getFeelsLike()))
                        .humidity(new BigDecimal(data.getHumidity()))
                        .pressure(new BigDecimal(data.getPressure()))
                        .build())
                .dt(data.getUnixTime())
                .build();
    }
}

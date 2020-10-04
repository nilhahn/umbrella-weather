package org.nilhahn.weather.storage;

import org.nilhahn.weather.storage.data.WeatherData;

import java.io.IOException;
import java.util.List;

public interface Storage {
    void init() throws IOException;
    void write(WeatherData weather) throws IOException;
    WeatherData read(String location) throws IOException;
    List<WeatherData> readAll(String location) throws IOException;
}

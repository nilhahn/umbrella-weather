package org.nilhahn.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.connection.Connector;
import org.nilhahn.weather.exception.NetworkException;
import org.nilhahn.weather.model.WeatherResponse;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class WeatherService {

    private final ObjectMapper objectMapper;
    private final Connector connector;
    private String url = "http://api.openweathermap.org/data/2.5/weather?q={location}&units=metric&appid={apiKey}";

    public WeatherService(Connector connector, String apiKey) {
        this.connector = connector;
        this.objectMapper = new ObjectMapper();

        this.url = url.replace("{apiKey}", apiKey);
    }

    public Optional<WeatherResponse> getWeatherForLocation(String location) {
        log.info("Request weather for location [{}]", location);

        if (location != null) {
            String destination = this.url.replace("{location}", location);
            try {
                String response = new String(this.connector.get(destination), StandardCharsets.UTF_8);
                return Optional.of(this.objectMapper.readValue(response, WeatherResponse.class));
            } catch (NetworkException | JsonProcessingException e) {
                log.error("Error while requesting weather for location [{}]. Message: [{}]", location, e.getMessage());
            }
        }
        return Optional.empty();
    }
}

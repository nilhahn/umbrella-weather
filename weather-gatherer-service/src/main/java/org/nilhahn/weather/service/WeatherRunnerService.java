package org.nilhahn.weather.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherRunnerService {

    private final WeatherService weatherService;
    private final StorageService storageService;
    private final LocationService locationService;

    private Integer interRequestSleep = 5000;

    public WeatherRunnerService(LocationService locationService, WeatherService weatherService, StorageService storageService) {
        this.weatherService = weatherService;
        this.storageService = storageService;
        this.locationService = locationService;
    }

    public void run() {
        locationService.getLocations().forEach(location -> {
            this.weatherService.getWeatherForLocation(location).ifPresent(this.storageService::store);
            this.sleep(this.interRequestSleep);
        });
        this.sleep(10000);
    }

    private void sleep(Integer ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.warn("Error while Thread.sleep", e);
        }
    }
}

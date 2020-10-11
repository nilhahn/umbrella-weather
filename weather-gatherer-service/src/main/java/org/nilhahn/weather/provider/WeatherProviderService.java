package org.nilhahn.weather.provider;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.service.LocationService;
import org.nilhahn.weather.service.StorageService;
import org.nilhahn.weather.service.WeatherService;

@Slf4j
public class WeatherProviderService implements Runnable {

    private final WeatherService weatherService;
    private final StorageService storageService;
    private final LocationService locationService;

    private final Integer interRequestSleep = 5000;
    private final Integer requestWindow = 3600000;
    private boolean running;

    public WeatherProviderService(LocationService locationService, WeatherService weatherService, StorageService storageService) {
        this.weatherService = weatherService;
        this.storageService = storageService;
        this.locationService = locationService;
    }

    @Override
    public void run() {
        this.running = true;
        while(this.running) {
            locationService.getLocations().forEach(location -> {
                log.info("Request for location [{}]", location);
                this.weatherService.getWeatherForLocation(location).ifPresent(this.storageService::store);
                this.sleep(this.interRequestSleep);
            });
            this.sleep((this.requestWindow - this.interRequestSleep));
        }
    }

    public void shutdown() {
        this.running = false;
    }

    private void sleep(Integer ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.warn("Error while Thread.sleep", e);
        }
    }
}

package org.nilhahn.weather;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.connection.HttpConnector;
import org.nilhahn.weather.service.CmdLineService;
import org.nilhahn.weather.service.StorageService;
import org.nilhahn.weather.service.WeatherService;

@Slf4j
public class Application {

    public static void main(String[] args) {
        StorageService storageService = new StorageService();
        CmdLineService cmdLineService = new CmdLineService();
        cmdLineService.parse(args);

        WeatherService service = new WeatherService(new HttpConnector(),
                cmdLineService.getCmdLineParameter(CmdLineService.Parameter.APIKEY)
                        .orElseThrow(() -> new RuntimeException("Api Key is missing"))
        );
        service.getWeatherForLocation(
                cmdLineService.getCmdLineParameter(CmdLineService.Parameter.LOCATION)
                        .orElseThrow(() -> new RuntimeException("Location is missing")))
                .ifPresent(storageService::store);
    }
}

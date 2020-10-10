package org.nilhahn.weather;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.connection.HttpConnector;
import org.nilhahn.weather.connection.RequestHandler;
import org.nilhahn.weather.service.CmdLineService;
import org.nilhahn.weather.service.DataProviderService;
import org.nilhahn.weather.service.LocationService;
import org.nilhahn.weather.service.StorageService;
import org.nilhahn.weather.service.WeatherRunnerService;
import org.nilhahn.weather.service.WeatherService;

@Slf4j
public class Application {

    public static void main(String[] args) {
        StorageService storageService = new StorageService();
        CmdLineService cmdLineService = new CmdLineService();
        LocationService locationService = LocationService.getInstance(5);
        RequestHandler requestHandler = new RequestHandler(locationService, storageService);
        DataProviderService dataProviderService = new DataProviderService(1, 8080, requestHandler);

        cmdLineService.parse(args);
        cmdLineService.getCmdLineParameter(CmdLineService.Parameter.LOCATION)
                .ifPresent(locationService::addLocation);

        WeatherService weatherService = new WeatherService(new HttpConnector(),
                cmdLineService.getCmdLineParameter(CmdLineService.Parameter.APIKEY)
                        .orElseThrow(() -> new RuntimeException("Api Key is missing"))
        );

        WeatherRunnerService weatherRunnerService = new WeatherRunnerService(
                locationService,
                weatherService,
                storageService
        );

        // start server thread
        dataProviderService.run();

        weatherRunnerService.run();
    }
}

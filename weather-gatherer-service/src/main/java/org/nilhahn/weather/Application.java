package org.nilhahn.weather;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.connection.HttpConnector;
import org.nilhahn.weather.connection.RequestHandler;
import org.nilhahn.weather.controller.AddController;
import org.nilhahn.weather.controller.DeleteController;
import org.nilhahn.weather.controller.ReadController;
import org.nilhahn.weather.model.connection.RequestMethod;
import org.nilhahn.weather.service.CmdLineService;
import org.nilhahn.weather.provider.DataProviderService;
import org.nilhahn.weather.service.LocationService;
import org.nilhahn.weather.service.StorageService;
import org.nilhahn.weather.provider.WeatherProviderService;
import org.nilhahn.weather.service.WeatherService;

@Slf4j
public class Application {

    public static void main(String[] args) throws InterruptedException {
        StorageService storageService = new StorageService();
        CmdLineService cmdLineService = new CmdLineService();
        LocationService locationService = LocationService.getInstance(5);
        RequestHandler requestHandler = new RequestHandler();

        requestHandler.registerHandler(RequestMethod.CREATE, new AddController(locationService));
        requestHandler.registerHandler(RequestMethod.READ, new ReadController(storageService));
        requestHandler.registerHandler(RequestMethod.DELETE, new DeleteController(locationService));

        DataProviderService dataProviderService = new DataProviderService(1, 8080, requestHandler);

        cmdLineService.parse(args);
        cmdLineService.getCmdLineParameter(CmdLineService.Parameter.LOCATION)
                .ifPresent(locationService::addLocation);

        WeatherService weatherService = new WeatherService(new HttpConnector(),
                cmdLineService.getCmdLineParameter(CmdLineService.Parameter.APIKEY)
                        .orElseThrow(() -> new RuntimeException("Api Key is missing"))
        );

        WeatherProviderService weatherProviderService = new WeatherProviderService(
                locationService,
                weatherService,
                storageService
        );

        // start server thread
        Thread serverThread = new Thread(dataProviderService);

        serverThread.start();
        weatherProviderService.run();

        serverThread.join();
    }
}

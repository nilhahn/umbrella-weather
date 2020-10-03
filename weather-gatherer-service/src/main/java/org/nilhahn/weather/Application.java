package org.nilhahn.weather;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.connection.HttpConnector;
import org.nilhahn.weather.model.WeatherResponse;
import org.nilhahn.weather.service.CmdLineService;
import org.nilhahn.weather.service.WeatherService;

import java.util.Optional;

@Slf4j
public class Application {

    public static void main(String[] args) {
        CmdLineService cmdLineService = new CmdLineService();
        cmdLineService.parse(args);

        WeatherService service = new WeatherService(new HttpConnector(),
                cmdLineService.getCmdLineParameter(CmdLineService.Parameter.APIKEY)
                .orElseThrow(() -> new RuntimeException("Api Key is missing"))
        );
        Optional<WeatherResponse> response = service.getWeatherForLocation(
                cmdLineService.getCmdLineParameter(CmdLineService.Parameter.LOCATION)
                        .orElseThrow(() -> new RuntimeException("Location is missing")));

        response.ifPresent(weather -> {
            log.info(weather.getWeather().get(0).getDescription());
            log.info(String.valueOf(weather.getMain().getTemp()));
        });
    }
}

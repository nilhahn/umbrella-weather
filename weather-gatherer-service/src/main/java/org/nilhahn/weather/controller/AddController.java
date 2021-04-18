package org.nilhahn.weather.controller;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.model.connection.Request;
import org.nilhahn.weather.model.connection.Response;
import org.nilhahn.weather.service.LocationService;

@Slf4j
public class AddController implements RequestController {

    public LocationService service;

    public AddController(LocationService service) {
        this.service = service;
    }

    @Override
    public void handle(Request request, Response response) {
        String[] requestData = request.getData().split("\\.");
        Integer storedLocations = 0;
        boolean success = false;

        if(requestData.length > 0 && requestData[0].equals("Location")) {
            success = this.service.addLocation(requestData[1]);
            storedLocations = this.service.getLocations().size();
        }

        if(success) {
            response.setData("Location.ADD.SUCCESSFUL");
            return;
        }

        if(storedLocations != 0) {
            response.setData("Location.ADD.FAILURE.STORED(" + storedLocations + ")");
            return;
        }

        response.setData("Location.ADD.FAILURE");
    }
}

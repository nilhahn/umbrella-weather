package org.nilhahn.weather.controller;

import org.nilhahn.weather.model.connection.Request;
import org.nilhahn.weather.model.connection.Response;
import org.nilhahn.weather.service.LocationService;

public class DeleteController implements RequestController {

    private LocationService service;

    public DeleteController(LocationService service) {
        this.service = service;
    }

    @Override
    public void handle(Request request, Response response) {
        String[] requestData = request.getData().split("\\.");

        if(requestData.length > 0 && "Location".equals(requestData[0])) {
            this.service.removeLocation(requestData[1]);
            response.setData("Location.DELETE.SUCCESSFUL");
            return;
        }

        response.setData("Location.DELETE.FAILURE");
    }
}

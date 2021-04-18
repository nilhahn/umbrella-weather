package org.nilhahn.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.model.connection.Request;
import org.nilhahn.weather.model.connection.Response;
import org.nilhahn.weather.service.StorageService;

import java.io.IOException;

@Slf4j
public class ReadController implements RequestController {

    private final StorageService service;
    private final ObjectMapper objectMapper;

    public ReadController(StorageService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(Request request, Response response) {
        String[] requestData = request.getData().split("\\.");

        if(requestData.length > 0 && requestData[0].equals("Location")) {
            try {
                response.setData(this.objectMapper.writeValueAsString(this.service.read(requestData[1])));
                return;
            } catch (IOException exception) {
                log.error("Failed to read weather data from storage [{}]", exception.getMessage());
            }
        }

        response.setData("Location.READ.FAILURE");
    }
}

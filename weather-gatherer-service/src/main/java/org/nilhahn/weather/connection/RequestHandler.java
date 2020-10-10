package org.nilhahn.weather.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nilhahn.weather.service.LocationService;
import org.nilhahn.weather.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class RequestHandler{

    private final StorageService storage;
    private final LocationService locationService;
    private final ObjectMapper objectMapper;

    public RequestHandler(LocationService locationService, StorageService storage) {
        this.storage = storage;
        this.locationService = locationService;
        this.objectMapper = new ObjectMapper();
    }

    public void handle(InputStream requestStream, OutputStream responseStream) throws IOException {
        Scanner input = new Scanner(requestStream);
        String request = input.nextLine();

        this.objectMapper.writeValue(responseStream, this.storage.read(request));
    }
}

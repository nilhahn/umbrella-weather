package org.nilhahn.weather.controller;

import org.nilhahn.weather.model.connection.Request;
import org.nilhahn.weather.model.connection.Response;

public interface RequestController {
    void handle(Request request, Response response);
}

package org.nilhahn.weather.connection;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.controller.RequestController;
import org.nilhahn.weather.model.connection.Request;
import org.nilhahn.weather.model.connection.RequestMethod;
import org.nilhahn.weather.model.connection.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class RequestHandler{

    private Map<RequestMethod, RequestController> handler = new HashMap<>();

    public void registerHandler(RequestMethod method, RequestController requestController) {
        handler.put(method, requestController);
    }

    public void handle(InputStream requestStream, OutputStream responseStream) throws IOException {
        Scanner input = new Scanner(requestStream);
        Response response = new Response();

        this.triggerHandler(Request.fromString(input.nextLine()), response);

        responseStream.write(response.toString().getBytes());
    }

    private void triggerHandler(Request request, Response response) {
        log.info("Received request {}", request);
        RequestController requestController = this.handler.get(request);

        if(requestController == null) {
            // TODO: trigger default
            return;
        }

        requestController.handle(request, response);
    }
}

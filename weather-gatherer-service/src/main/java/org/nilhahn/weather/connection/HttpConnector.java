package org.nilhahn.weather.connection;

import org.apache.commons.io.IOUtils;
import org.nilhahn.weather.exception.NetworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

public class HttpConnector implements Connector{
    final Logger log = LoggerFactory.getLogger(HttpConnector.class);

    public byte[] get(String destination, Map<String, String> header, Proxy proxy) throws NetworkException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(destination).openConnection(proxy);
            connection.setRequestMethod("GET");
            header.forEach(connection::setRequestProperty);
            return IOUtils.toByteArray(connection);
        } catch (IOException e) {
            log.error("Failed to request resource from [{}]", destination, e);
            throw new NetworkException(String.format("Error while preparing http request %s", e.getMessage()));
        }
    }

    public byte[] get(String destination, Map<String, String> header) throws NetworkException {
        return this.get(destination, header, Proxy.NO_PROXY);
    }

    public byte[] get(String destination) throws NetworkException {
        return this.get(destination, Collections.EMPTY_MAP, Proxy.NO_PROXY);
    }
}

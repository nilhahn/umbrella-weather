package org.nilhahn.weather.connection;

import org.nilhahn.weather.exception.NetworkException;

import java.net.Proxy;
import java.util.Map;

public interface Connector {
    byte[] get(String destination, Map<String, String> header,  Proxy proxy) throws NetworkException;
    byte[] get(String destination, Map<String, String> header) throws NetworkException;
    byte[] get(String destination) throws NetworkException;
}

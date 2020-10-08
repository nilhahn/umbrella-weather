package org.nilhahn.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.nilhahn.weather.connection.RequestHandler;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class DataProviderService implements Runnable {

    private final Integer backlog;
    private final Integer port;
    private final RequestHandler handler;

    public DataProviderService(Integer backlog, Integer port, RequestHandler handler) {
        this.port = port;
        this.backlog = backlog;
        this.handler = handler;
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port, backlog, InetAddress.getLoopbackAddress());
            while (true) {
                Socket client = null;
                try {
                    log.info("Start new server");
                    client = server.accept();
                    this.handleConnection(client);
                    log.info("Connection handled successful");
                } catch (IOException exception) {
                    log.error("Error in connection", exception);
                } finally {
                    this.close(client);
                }
            }
        } catch (IOException exception) {
            log.error("Error in connection", exception);
        } finally {
            this.close(server);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException exception) {
                log.warn("Error while closing closeable", exception);
            }
        }
    }

    private void handleConnection(Socket client) throws IOException {
        this.handler.handle(client.getInputStream(), client.getOutputStream());
    }
}

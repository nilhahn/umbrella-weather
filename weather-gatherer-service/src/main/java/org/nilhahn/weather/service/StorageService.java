package org.nilhahn.weather.service;

import org.nilhahn.weather.model.WeatherResponse;
import org.nilhahn.weather.storage.Storage;
import org.nilhahn.weather.storage.StorageFactory;

import java.util.List;

import java.util.Collections;

public class StorageService {

    private Storage storage;

    public StorageService(String storageMethod) {
        this.storage = StorageFactory.getInstance(storageMethod)
                .orElseThrow();
    }

    public void store(WeatherResponse weather) {
    }

    public WeatherResponse read() {
        return null;
    }

    public List<WeatherResponse> readBulk() {
        return Collections.emptyList();
    }
}

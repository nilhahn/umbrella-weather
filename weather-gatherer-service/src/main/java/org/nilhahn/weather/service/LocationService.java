package org.nilhahn.weather.service;

import java.util.ArrayList;
import java.util.List;

public class LocationService {

    private static LocationService instance = null;
    private Integer maxAmountOfLocations;
    private List<String> locations;

    public static LocationService getInstance(Integer maxAmountOfLocations) {
        if(instance == null) {
            instance = new LocationService(maxAmountOfLocations);
        }
        return instance;
    }

    public boolean addLocation(String location) {
        boolean result = false;
        if(this.maxAmountOfLocations > this.locations.size() && !this.locations.contains(location)) {
            result = this.locations.add(location);
        }
        return result;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public boolean removeLocation(String location) {
        return this.locations.remove(location);
    }

    private LocationService(Integer maxAmountOfLocations) {
        this.maxAmountOfLocations = maxAmountOfLocations;
        this.locations = new ArrayList<>();
    }
}

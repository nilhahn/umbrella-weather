package org.nilhahn.weather.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.nilhahn.weather.storage.data.WeatherData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileStorage implements Storage {

    private ObjectMapper objectMapper = null;
    private String fileNameBase = "weather";

    @Override
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void write(WeatherData weather) {
        FileWriterWithEncoding writer = null;
        try {
            File locationFile = this.initLocationFile(weather.getCityName());
            writer = new FileWriterWithEncoding(locationFile, "UTF-8", true);
            IOUtils.write(this.objectMapper.writeValueAsString(weather),
                    writer);
        } catch (IOException exception) {
            log.error("Error while handling file for city [{}]", weather.getCityName());
        } finally {
            IOUtils.closeQuietly(writer, exception ->
                    log.error("Error while trying to close file", exception));
        }
    }

    @Override
    public WeatherData read(String location) throws IOException {
        List<String> data = this.readAllAsString(location);
        return this.objectMapper.readValue(data.get(data.size() - 1), WeatherData.class);
    }

    @Override
    public List<WeatherData> readAll(String location) throws IOException {
        List<String> content = this.readAllAsString(location);
        List<WeatherData> weatherData = new ArrayList<>();
        content.forEach(elem -> {
            try {
                weatherData.add(this.objectMapper.readValue(elem, WeatherData.class));
            } catch (JsonProcessingException e) {
                log.error("Error while mapping data", e);
            }
        });
        return weatherData;
    }

    private List<String> readAllAsString(String location) throws IOException {
        File locationFile = this.initLocationFile(location);
        FileReader fileReader = new FileReader(locationFile);
        return IOUtils.readLines(fileReader);
    }

    private File initLocationFile(String cityName) throws IOException {
        File file = new File(this.fileNameBase + cityName + ".txt");
        if (!file.exists()) {
            boolean result = file.createNewFile() || file.setReadable(true) || file.setWritable(true);
            log.info("Storage file [{}] created with result [{}]", file.getName(), result);
        }
        return file;
    }
}

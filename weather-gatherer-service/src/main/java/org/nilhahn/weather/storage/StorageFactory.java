package org.nilhahn.weather.storage;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class StorageFactory {

    private static Storage storage = null;

    public static Optional<Storage> getInstance() {
        try {
            if (storage == null) {
                log.info("Create new Storage");
                storage = new FileStorage();
                storage.init();
            }
        } catch (IOException exception) {
            log.error("Failed to initalize storage", exception);
            return Optional.empty();
        }
        return Optional.of(storage);
    }
}

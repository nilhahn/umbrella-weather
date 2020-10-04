package org.nilhahn.weather.storage;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
public class StorageFactory {

    public static Optional<Storage> getInstance(String method) {
        FileStorage storage = new FileStorage();
        log.info("Create new Storage");
        storage.init();
        return Optional.of(storage);
    }
}

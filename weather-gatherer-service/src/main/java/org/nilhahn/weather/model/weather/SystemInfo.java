package org.nilhahn.weather.model.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemInfo {
    private Long type;
    private Long id;
    private Long message;
    private Long sunrise;
    private Long sunset;

    private String country;
}

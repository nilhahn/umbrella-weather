package org.nilhahn.weather.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Coordinates {
    private BigDecimal lon;
    private BigDecimal lat;
}

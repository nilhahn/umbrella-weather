package org.nilhahn.weather.model.weather;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Wind {
    private BigDecimal speed;
    private Long deg;
}

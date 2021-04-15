package org.nilhahn.weather.model.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {
    private Long id;
    private String main;
    private String description;
    private String icon;
}

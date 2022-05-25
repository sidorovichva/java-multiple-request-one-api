package com.vs.javamultiplerequestoneapi.models.responses.ota;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

    private String id;
    private String name;
    private String countryId;
    private String forecast;
    private String shortUrlForecast;
    Map<String, String> mlName;
}

package com.vs.javamultiplerequestoneapi.models.responses.ota;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {

    private String name;
    private String code;
    private String cityId;
    private String timeZone;
    private String map;
    private String shortUrlMap;
    Map<String, String> mlName;
}

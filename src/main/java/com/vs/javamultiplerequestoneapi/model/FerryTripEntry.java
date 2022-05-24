package com.vs.javamultiplerequestoneapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FerryTripEntry {

    @JsonProperty("localdatetime")
    private LocalDateTime localDateTime;
    private String port;
}

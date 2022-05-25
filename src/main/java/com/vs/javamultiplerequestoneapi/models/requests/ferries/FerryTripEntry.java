package com.vs.javamultiplerequestoneapi.models.requests.ferries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FerryTripEntry {

    @JsonProperty("localdatetime")
    private LocalDateTime localDateTime;
    private String port;
}

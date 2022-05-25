package com.vs.javamultiplerequestoneapi.model.flights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSub {

    private String airport;
    private Instant instant;
    private String datetime;
    private LocalDateTime localdatetime;
}

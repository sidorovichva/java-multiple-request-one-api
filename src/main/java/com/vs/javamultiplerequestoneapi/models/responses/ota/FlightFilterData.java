package com.vs.javamultiplerequestoneapi.models.responses.ota;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightFilterData {

    private boolean blackListed;
    private String blackListedReason;
    private boolean flightNotFound;
    private boolean flightHasAnomaly;
    private boolean flightTooClose;
    private boolean oldFlight;
    private boolean illegalFlightId;
}

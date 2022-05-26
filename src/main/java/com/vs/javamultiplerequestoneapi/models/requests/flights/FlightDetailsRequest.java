package com.vs.javamultiplerequestoneapi.models.requests.flights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDetailsRequest {

    private int flightNumber;
    private String airlineId;
    private String departingDate;
    private String originAirportId;
    private String destinationAirportId;
}

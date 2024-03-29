package com.vs.javamultiplerequestoneapi.models.results.raw;

import com.vs.javamultiplerequestoneapi.models.requests.flights.PricingFlightsRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightRawTestResult {

    private LocalDate dateTime;
    private PricingFlightsRequest request;
    private List<Double> risk;
}

package com.vs.javamultiplerequestoneapi.models.requests.flights;

import com.vs.javamultiplerequestoneapi.models.requests.PricingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingFlightsRequest implements PricingRequest {

    private List<Flight> flight;
    private List<Integer> alloweddelay = new LinkedList<>();
    private List<Boolean> givenFlightNotCancelled = new LinkedList<>();
}

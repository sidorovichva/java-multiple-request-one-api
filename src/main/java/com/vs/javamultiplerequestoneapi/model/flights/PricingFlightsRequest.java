package com.vs.javamultiplerequestoneapi.model.flights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingFlightsRequest {

    private List<Flight> flight;
}

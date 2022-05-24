package com.vs.javamultiplerequestoneapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingFerryRequest {

    private List<Disruption> disruptions = new ArrayList<>();
    private List<PricingFerryItinerary> itineraries = new ArrayList<>();
}

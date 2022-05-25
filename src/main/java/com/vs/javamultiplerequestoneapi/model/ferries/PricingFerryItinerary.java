package com.vs.javamultiplerequestoneapi.model.ferries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingFerryItinerary {

    private String id;
    private String operator;
    private FerryTripEntry departure;
    private FerryTripEntry arrival;
}

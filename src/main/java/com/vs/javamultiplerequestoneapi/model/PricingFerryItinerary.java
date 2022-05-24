package com.vs.javamultiplerequestoneapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingFerryItinerary {

    private FerryTripEntry arrival;
    private FerryTripEntry departure;
    private String id;
    private String operator;
}

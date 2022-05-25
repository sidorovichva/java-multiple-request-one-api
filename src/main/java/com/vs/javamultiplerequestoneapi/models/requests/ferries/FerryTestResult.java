package com.vs.javamultiplerequestoneapi.models.requests.ferries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FerryTestResult {

    private LocalDate dateTime;
    private PricingFerryRequest request;
    private double risk;
}

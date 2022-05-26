package com.vs.javamultiplerequestoneapi.models.requests.results;

import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FerryTestResult {

    private LocalDate dateTime;
    private PricingFerryRequest request;
    private List<Double> risk;
}

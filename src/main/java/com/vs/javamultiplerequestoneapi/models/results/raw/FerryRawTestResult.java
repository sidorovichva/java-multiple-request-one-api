package com.vs.javamultiplerequestoneapi.models.results.raw;

import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FerryRawTestResult {

    private LocalDate dateTime;
    private PricingFerryRequest request;
    private List<Double> risk;
}

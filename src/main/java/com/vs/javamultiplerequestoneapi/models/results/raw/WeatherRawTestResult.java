package com.vs.javamultiplerequestoneapi.models.results.raw;

import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
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
public class WeatherRawTestResult {

    private LocalDate dateTime;
    private PricingWXRequest request;
    private List<Double> risk;
}

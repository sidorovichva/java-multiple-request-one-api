package com.vs.javamultiplerequestoneapi.models.responses.pricing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingResponse {

    private List<Double> error;
    private String id;
    private String isAnomaly;
    private List<String> components;
    private List<Double> probability;
    private List<String> targets;
    private PricingComment comment;
}

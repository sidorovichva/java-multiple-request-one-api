package com.vs.javamultiplerequestoneapi.models.responses.pricing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingComment {

    private String additional;
    private String anomaly;
    private String unknown;
}

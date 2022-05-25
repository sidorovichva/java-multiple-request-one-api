package com.vs.javamultiplerequestoneapi.models.requests.ferries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disruption {

    private Double delay;
    private boolean givenNotCancelled = true;
}

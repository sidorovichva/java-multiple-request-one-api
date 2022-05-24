package com.vs.javamultiplerequestoneapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disruption {

    private Double delay;
    private boolean givenNotCancelled = true;
}

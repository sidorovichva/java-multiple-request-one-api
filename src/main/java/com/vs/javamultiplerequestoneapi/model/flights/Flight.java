package com.vs.javamultiplerequestoneapi.model.flights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    private String id;
    private int number;
    private String operator;
    private String marketer;
    private FlightSub departure;
    private FlightSub arrival;
}

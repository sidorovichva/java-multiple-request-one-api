package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.models.requests.flights.PricingFlightsRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FlightTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService service;

    @GetMapping("/getFlightTestResult")
    public List<FlightTestResult> getFlightTestResultByCsvFile(@RequestParam int quantity) throws IOException {
        return service.getFlightTestResult(quantity);
    }

    @GetMapping("/getListOfSingleTestResults")
    public List<SingleTestResult> getListOfSingleTestResults(@RequestParam int quantity) throws IOException {
        return service.getSingleTestResults(quantity);
    }

    @GetMapping("/execute/byRequest")
    public List<Double> getRiskByOneRequest(@RequestBody PricingFlightsRequest request) {
        return service.getRiskByOneRequest(request);
    }
}

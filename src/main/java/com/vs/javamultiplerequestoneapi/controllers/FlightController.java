package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.enums.DP;
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
    public List<FlightTestResult> getFlightTestResultByCsvFile(
            @RequestParam int quantity,
            @RequestParam String env
    ) throws IOException {
        return service.getFlightTestResultByCsvFile(quantity, DP.FlightEnvs.CI.getEnvironment(env));
    }

    @GetMapping("/getFlightTestResultAndSave")
    public List<SingleTestResult> getListOfSingleTestResults(
            @RequestParam int quantity,
            @RequestParam String env
    ) throws IOException {
        return service.getSingleTestResults(quantity, DP.FlightEnvs.CI.getEnvironment(env));
    }

    /*@GetMapping("/execute/byRequest")
    public List<Double> getRiskByOneRequest(@RequestBody PricingFlightsRequest request) {
        return service.getRiskByOneRequest(request);
    }*/

    @GetMapping("/flightTestSample/{api}/{quantity}")
    public Integer getFlightTestSample(@PathVariable String api, @PathVariable Integer quantity) {
        return service.getFlightTestSample(api, quantity);
    }
}

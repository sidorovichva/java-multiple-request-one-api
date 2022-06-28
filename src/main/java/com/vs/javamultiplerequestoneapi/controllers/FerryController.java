package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FerryTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.services.FerryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ferries")
@RequiredArgsConstructor
public class FerryController {

    private final FerryService service;

    @GetMapping("/execute")
    public List<FerryTestResult> getFerryTestResultByCsvFile(
            @RequestParam int quantity,
            @RequestParam String env,
            @RequestParam int delay
    ) throws IOException {
        return service.getFerryTestResult(quantity, DP.FerryEnvs.CI.getEnvironment(env), delay);
    }

    @GetMapping("/execute/byRequest")
    public List<Double> getRiskByOneRequest(
            @RequestBody PricingFerryRequest request,
            @RequestParam String env
    ) {
        return service.getRiskByOneRequest(request, DP.FerryEnvs.CI.getEnvironment(env));
    }

    @GetMapping("/getFerryTestResultAndSave")
    public List<SingleTestResult> getListOfSingleTestResults(
            @RequestParam int quantity,
            @RequestParam String env,
            @RequestParam int delay
    ) throws IOException {
        return service.getSingleTestResults(quantity, DP.FerryEnvs.CI.getEnvironment(env), delay);
    }
}

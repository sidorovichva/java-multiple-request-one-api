package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FerryTestResult;
import com.vs.javamultiplerequestoneapi.services.FerryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ferries")
@RequiredArgsConstructor
public class FerryController {

    private final FerryService ferryService;

    @GetMapping("/execute")
    public List<FerryTestResult> getFerryTestResultByCsvFile(@RequestParam int quantity) throws IOException {
        return ferryService.getFerryTestResult(quantity);
    }

    @GetMapping("/execute/byRequest")
    public List<Double> getRiskByOneRequest(@RequestBody PricingFerryRequest request) {
        return ferryService.getRiskByOneRequest(request);
    }
}

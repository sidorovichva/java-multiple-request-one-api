package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.models.requests.ferries.FerryTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.services.FerryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ferries")
@RequiredArgsConstructor
public class FerryController {

    private final FerryService ferryService;

    @GetMapping("/execute")
    public List<FerryTestResult> getFerryTestResultByCsvFile(@RequestParam int quantity) throws IOException {
        return ferryService.getFerryTestResult(quantity);
    }

    @GetMapping("/execute/riskOnly")
    public List<Double> getRiskByCsvFile(@RequestParam int quantity) throws IOException {
        return ferryService.getFerryTestResult(quantity).stream()
                .map(FerryTestResult::getRisk)
                .collect(Collectors.toList());
    }

    @GetMapping("/execute/byRequest")
    public List<Double> getRiskByOneRequest(@RequestBody PricingFerryRequest request) throws IOException {
        return ferryService.getRiskByOneRequest(request);
    }
}

package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
import com.vs.javamultiplerequestoneapi.models.results.WeatherTestResult;
import com.vs.javamultiplerequestoneapi.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService service;

    @GetMapping("/execute/byRequest")
    public List<Double> getRiskByOneRequest(
            @RequestBody PricingWXRequest request,
            @RequestParam String env,
            @RequestParam String product
    ) {
        return service.getRiskByOneRequest(request, product, DP.WeatherEnvs.CI.getEnvironment(env));
    }

    @GetMapping("/getWeatherTestResultAndSave")
    public List<WeatherTestResult> getListOfSingleTestResults(
            @RequestParam int quantity,
            @RequestParam String product,
            @RequestParam String env
    ) throws IOException {
        return service.getSingleTestResults(quantity, product, DP.WeatherEnvs.CI.getEnvironment(env));
    }
}

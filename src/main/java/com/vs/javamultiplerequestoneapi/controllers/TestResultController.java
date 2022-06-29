package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.models.results.TransportationTestResult;
import com.vs.javamultiplerequestoneapi.models.results.WeatherTestResult;
import com.vs.javamultiplerequestoneapi.services.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class TestResultController {

    private final TestResultService service;

    @GetMapping("/transport/last_x_days")
    public List<TransportationTestResult> getAllTransportTestResultsInLastXDays(@RequestParam int days) {
        return service.getAllTransportTestResultsInLastXDays(days);
    }

    @GetMapping("/weather/last_x_days")
    public List<WeatherTestResult> getAllWeatherTestResultsInLastXDays(@RequestParam int days) {
        return service.getAllWeatherTestResultsInLastXDays(days);
    }

    @GetMapping("/transport/delay_in_last_x_days")
    public List<TransportationTestResult> getAllSingleTestResultsByDelayInLastXDays(
            @RequestParam int delay,
            @RequestParam int days
    ) {
        return service.getAllSingleTestResultsByDelayInLastXDays(delay, days);
    }
}

package com.vs.javamultiplerequestoneapi.controllers;

import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.TestResult;
import com.vs.javamultiplerequestoneapi.services.SingleTestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class SingleTestResultController {

    private final SingleTestResultService service;

    @GetMapping("/all/last_x_days")
    public List<SingleTestResult> getAllSingleTestResultsInLastXDays(@RequestParam int days) {
        return service.getAllSingleTestResultsInLastXDays(days);
    }

    @GetMapping("/all/delay_in_last_x_days")
    public List<SingleTestResult> getAllSingleTestResultsByDelayInLastXDays(
            @RequestParam int delay,
            @RequestParam int days
    ) {
        return service.getAllSingleTestResultsByDelayInLastXDays(delay, days);
    }
}

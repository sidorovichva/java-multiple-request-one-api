package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.builders.PricingWeatherRequestBuilder;
import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FerryTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.WeatherTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
import com.vs.javamultiplerequestoneapi.repositories.SingleTestResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService extends AbstractService {

    private final PricingWeatherRequestBuilder weatherRequestBuilder;
    private final PricingResponseFetcher pricingResponseFetcher;
    private final SingleTestResultRepository repository;

    public List<WeatherTestResult> getWeatherTestResult(int quantity, String product, String link) throws IOException {
        List<PricingWXRequest> listOfRequests = weatherRequestBuilder.getListOfWeatherRequests(quantity);

        return listOfRequests.stream()
                .map(request -> WeatherTestResult.builder()
                        .request(request)
                        .risk(pricingResponseFetcher.getPricingResponseForWeather(request, product, link).getProbability())
                        .dateTime(LocalDate.now())
                        .build())
                .filter(result -> {
                    if (result == null) {
                        log.warn("result was filtered: {}", result);
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public List<SingleTestResult> getSingleTestResults(int quantity, String product, String link) throws IOException {
        final List<WeatherTestResult> ferryTestResults = getWeatherTestResult(quantity, product, link);

        List<SingleTestResult> singleTestResults = new ArrayList<>();

        ferryTestResults.forEach(result -> {
            for (int i = 0; i < result.getRisk().size(); i++) {
                singleTestResults.add(SingleTestResult.builder()
                        .id(result.getRequest().getEvents().get(0).getId())
                        .date(LocalDateTime.now())
                        .delay(0)
                        .notCancelled(false)
                        .risk(result.getRisk().get(i))
                        .build());
            }
        });

        saveToCSV(singleTestResults, DP.Weather);
        repository.saveAll(singleTestResults);

        return singleTestResults;
    }

    public List<Double> getRiskByOneRequest(PricingWXRequest request, String product, String link) {
        return pricingResponseFetcher.getPricingResponseForWeather(request, product, link).getProbability();
    }
}

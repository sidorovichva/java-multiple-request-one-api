package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.builders.PricingWeatherRequestBuilder;
import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.models.results.WeatherTestResult;
import com.vs.javamultiplerequestoneapi.models.results.raw.WeatherRawTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
import com.vs.javamultiplerequestoneapi.repositories.WeatherTestResultRepository;
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
    private final WeatherTestResultRepository repository;

    public List<WeatherRawTestResult> getWeatherTestResult(int quantity, String product, String link) throws IOException {
        List<PricingWXRequest> listOfRequests = weatherRequestBuilder.getListOfWeatherRequests(quantity);

        return listOfRequests.stream()
                .map(request -> WeatherRawTestResult.builder()
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

    public List<WeatherTestResult> getSingleTestResults(int quantity, String product, String link) throws IOException {
        final List<WeatherRawTestResult> weatherRawTestResults = getWeatherTestResult(quantity, product, link);

        List<WeatherTestResult> weatherTestResults = new ArrayList<>();

        weatherRawTestResults.forEach(result -> {
            for (int i = 0; i < result.getRisk().size(); i++) {
                weatherTestResults.add(WeatherTestResult.builder()
                        .id(result.getRequest().getEvents().get(0).getId())
                        .date(LocalDateTime.now())
                        .amount(result.getRequest().getAmount().get(i))
                        .days(Integer.parseInt(result.getRequest().getDays().get(i)))
                        .risk(result.getRisk().get(i))
                        .build());
            }
        });

        saveToCSVWeather(weatherTestResults, DP.Weather);
        repository.saveAll(weatherTestResults);

        return weatherTestResults;
    }

    public List<Double> getRiskByOneRequest(PricingWXRequest request, String product, String link) {
        return pricingResponseFetcher.getPricingResponseForWeather(request, product, link).getProbability();
    }
}

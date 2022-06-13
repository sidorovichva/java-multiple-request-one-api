package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService extends AbstractService {

    private final PricingResponseFetcher pricingResponseFetcher;

    public List<Double> getRiskByOneRequest(PricingWXRequest request, String product, String link) {
        return pricingResponseFetcher.getPricingResponseForWeather(request, product, link).getProbability();
    }
}

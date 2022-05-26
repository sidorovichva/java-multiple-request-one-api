package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.builders.PricingFerriesRequestBuilder;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FerryTestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FerryService {

    private final PricingFerriesRequestBuilder ferriesRequestPreparation;
    private final PricingResponseFetcher pricingResponseFetcher;

    public List<FerryTestResult> getFerryTestResult(int quantity) throws IOException {
        List<PricingFerryRequest> listOfRequests = ferriesRequestPreparation.getListOfFerryRequests(quantity);

        return listOfRequests.stream()
                .map(request -> FerryTestResult.builder()
                                .request(request)
                                .risk(pricingResponseFetcher.getPricingResponseForFerry(DP.Ferry, request).getProbability())
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

    public List<Double> getRiskByOneRequest(PricingFerryRequest request) {
        return pricingResponseFetcher.getPricingResponseForFerry(DP.Ferry, request).getProbability();
    }
}

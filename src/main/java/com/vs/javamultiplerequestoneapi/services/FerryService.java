package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.builders.PricingFerriesRequestBuilder;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FerryTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.FlightTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
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
public class FerryService extends AbstractService{

    private final PricingFerriesRequestBuilder ferriesRequestPreparation;
    private final PricingResponseFetcher pricingResponseFetcher;
    private final SingleTestResultRepository repository;

    public List<FerryTestResult> getFerryTestResult(int quantity, String link, int delay) throws IOException {
        List<PricingFerryRequest> listOfRequests = ferriesRequestPreparation.getListOfFerryRequests(quantity, delay);

        return listOfRequests.stream()
                .map(request -> FerryTestResult.builder()
                                .request(request)
                                .risk(pricingResponseFetcher.getPricingResponseForFerry(link, request).getProbability())
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

    public List<SingleTestResult> getSingleTestResults(int quantity, String link, int delay) throws IOException {
        final List<FerryTestResult> ferryTestResults = getFerryTestResult(quantity, link, delay);

        List<SingleTestResult> singleTestResults = new ArrayList<>();

        ferryTestResults.forEach(result -> {
            for (int i = 0; i < result.getRisk().size(); i++) {
                singleTestResults.add(SingleTestResult.builder()
                        .id(result.getRequest().getItineraries().get(0).getId())
                        .date(LocalDateTime.now())
                        .delay(result.getRequest().getDisruptions().get(0).getDelay().intValue())
                        .notCancelled(result.getRequest().getDisruptions().get(0).isGivenNotCancelled())
                        .risk(result.getRisk().get(i))
                        .build());
            }
        });

        saveToCSV(singleTestResults, DP.Ferry);
        repository.saveAll(singleTestResults);

        return singleTestResults;
    }

    public List<Double> getRiskByOneRequest(PricingFerryRequest request, String link) {
        return pricingResponseFetcher.getPricingResponseForFerry(link, request).getProbability();
    }
}

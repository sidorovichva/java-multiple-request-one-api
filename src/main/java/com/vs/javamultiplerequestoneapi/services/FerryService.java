package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.builders.PricingFerriesRequestBuilder;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.results.raw.FerryRawTestResult;
import com.vs.javamultiplerequestoneapi.models.results.TransportationTestResult;
import com.vs.javamultiplerequestoneapi.repositories.TransportationTestResultRepository;
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
    private final TransportationTestResultRepository repository;

    public List<FerryRawTestResult> getFerryTestResult(int quantity, String link) throws IOException {
        List<PricingFerryRequest> listOfRequests = ferriesRequestPreparation.getListOfFerryRequests(quantity);

        return listOfRequests.stream()
                .map(request -> FerryRawTestResult.builder()
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

    public List<TransportationTestResult> getSingleTestResults(int quantity, String link) throws IOException {
        final List<FerryRawTestResult> ferryRawTestResults = getFerryTestResult(quantity, link);

        List<TransportationTestResult> transportationTestResults = new ArrayList<>();

        ferryRawTestResults.forEach(result -> {
            for (int i = 0; i < result.getRisk().size(); i++) {
                transportationTestResults.add(TransportationTestResult.builder()
                        .id(result.getRequest().getItineraries().get(0).getId())
                        .date(LocalDateTime.now())
                        .delay(result.getRequest().getDisruptions().get(i).getDelay().intValue())
                        .notCancelled(result.getRequest().getDisruptions().get(i).isGivenNotCancelled())
                        .risk(result.getRisk().get(i))
                        .build());
            }
        });

        saveToCSVTransport(transportationTestResults, DP.Ferry);
        repository.saveAll(transportationTestResults);

        return transportationTestResults;
    }

    public List<Double> getRiskByOneRequest(PricingFerryRequest request, String link) {
        return pricingResponseFetcher.getPricingResponseForFerry(link, request).getProbability();
    }
}

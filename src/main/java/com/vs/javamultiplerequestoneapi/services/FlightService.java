package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.builders.FlightDetailsRequestBuilder;
import com.vs.javamultiplerequestoneapi.builders.PricingFlightRequestBuilder;
import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.enums.SOURCE;
import com.vs.javamultiplerequestoneapi.fetchers.FlightDetailsFetcher;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.models.requests.flights.FlightDetailsRequest;
import com.vs.javamultiplerequestoneapi.models.requests.flights.PricingFlightsRequest;
import com.vs.javamultiplerequestoneapi.models.requests.results.FlightTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.models.responses.ota.DetailedFlightDTO;
import com.vs.javamultiplerequestoneapi.repositories.SingleTestResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightDetailsRequestBuilder flightDetailsRequestBuilder;
    private final PricingFlightRequestBuilder pricingFlightRequestBuilder;
    private final FlightDetailsFetcher fetcher;
    private final PricingResponseFetcher pricingResponseFetcher;
    private final SingleTestResultRepository repository;

    public List<FlightTestResult> getFlightTestResult(int quantity) throws IOException {
        final List<FlightDetailsRequest> flightDetailsRequests = flightDetailsRequestBuilder.buildListOfFlightDetailsRequests(quantity);

        final List<DetailedFlightDTO> detailedFlightDTOs = flightDetailsRequests.stream()
                .map(request -> fetcher.getFlightDetailOtaResponse(SOURCE.ota, request))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        final List<PricingFlightsRequest> pricingFlightsRequests = detailedFlightDTOs.stream()
                .map(pricingFlightRequestBuilder::buildPricingFlightRequest)
                .collect(Collectors.toList());

        final List<FlightTestResult> flightTestResults = pricingFlightsRequests.stream()
                .map(request -> FlightTestResult.builder()
                        .request(request)
                        .risk(pricingResponseFetcher.getPricingResponseForFlight(DP.Flight, request).getProbability())
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

        return flightTestResults;
    }

    public List<SingleTestResult> getSingleTestResults(int quantity) throws IOException {
        final List<FlightTestResult> flightTestResults = getFlightTestResult(quantity);

        List<SingleTestResult> singleTestResults = new ArrayList<>();

        flightTestResults.forEach(result -> {
            for (int i = 0; i < result.getRisk().size(); i++) {
                singleTestResults.add(SingleTestResult.builder()
                        .id(result.getRequest().getFlight().get(0).getId())
                        .date(LocalDateTime.now())
                        .delay(result.getRequest().getAlloweddelay().get(i))
                        .notCancelled(result.getRequest().getGivenFlightNotCancelled().get(i))
                        .risk(result.getRisk().get(i))
                        .build());
            }
        });

        repository.saveAll(singleTestResults);

        return singleTestResults;
    }

    public List<Double> getRiskByOneRequest(PricingFlightsRequest request) {
        return pricingResponseFetcher.getPricingResponseForFlight(DP.Flight, request).getProbability();
    }
}

package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.builders.FlightDetailsRequestBuilder;
import com.vs.javamultiplerequestoneapi.builders.PricingFlightRequestBuilder;
import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.enums.SOURCE;
import com.vs.javamultiplerequestoneapi.fetchers.FlightDetailsFetcher;
import com.vs.javamultiplerequestoneapi.fetchers.FlightTestSample;
import com.vs.javamultiplerequestoneapi.fetchers.PricingResponseFetcher;
import com.vs.javamultiplerequestoneapi.models.requests.flights.FlightDetailsRequest;
import com.vs.javamultiplerequestoneapi.models.requests.flights.PricingFlightsRequest;
import com.vs.javamultiplerequestoneapi.models.results.raw.FlightRawTestResult;
import com.vs.javamultiplerequestoneapi.models.results.TransportationTestResult;
import com.vs.javamultiplerequestoneapi.models.responses.ota.DetailedFlightDTO;
import com.vs.javamultiplerequestoneapi.repositories.TransportationTestResultRepository;
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
public class FlightService extends AbstractService {

    private final FlightDetailsRequestBuilder flightDetailsRequestBuilder;
    private final PricingFlightRequestBuilder pricingFlightRequestBuilder;
    private final FlightDetailsFetcher fetcher;
    private final PricingResponseFetcher pricingResponseFetcher;
    private final TransportationTestResultRepository repository;
    private final FlightTestSample flightTestSample;

    public List<FlightRawTestResult> getFlightTestResultByCsvFile(int quantity, String link) throws IOException {
        final List<FlightDetailsRequest> flightDetailsRequests = flightDetailsRequestBuilder.buildListOfFlightDetailsRequests(quantity);

        final List<DetailedFlightDTO> detailedFlightDTOs = flightDetailsRequests.stream()
                .map(request -> fetcher.getFlightDetailOtaResponse(SOURCE.ota, request))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        final List<PricingFlightsRequest> pricingFlightsRequests = detailedFlightDTOs.stream()
                .map(pricingFlightRequestBuilder::buildPricingFlightRequest)
                .collect(Collectors.toList());

        final List<FlightRawTestResult> flightRawTestResults = pricingFlightsRequests.stream()
                .map(request -> FlightRawTestResult.builder()
                        .request(request)
                        .risk(pricingResponseFetcher.getPricingResponseForFlight(link, request).getProbability())
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

        return flightRawTestResults;
    }

    public List<TransportationTestResult> getSingleTestResults(int quantity, String link) throws IOException {
        final List<FlightRawTestResult> flightRawTestResults = getFlightTestResultByCsvFile(quantity, link);

        List<TransportationTestResult> transportationTestResults = new ArrayList<>();

        flightRawTestResults.forEach(result -> {
            for (int i = 0; i < result.getRisk().size(); i++) {
                transportationTestResults.add(TransportationTestResult.builder()
                        .id(result.getRequest().getFlight().get(0).getId())
                        .date(LocalDateTime.now())
                        .delay(result.getRequest().getAlloweddelay().get(i))
                        .notCancelled(result.getRequest().getGivenFlightNotCancelled().get(i))
                        .risk(result.getRisk().get(i))
                        .build());
            }
        });

        saveToCSVTransport(transportationTestResults, DP.Flight);
        repository.saveAll(transportationTestResults);

        return transportationTestResults;
    }

    /*public List<Double> getRiskByOneRequest(PricingFlightsRequest request) {
        return pricingResponseFetcher.getPricingResponseForFlight(DP.Flight, request).getProbability();
    }*/

    public Integer getFlightTestSample(String service, int quantity) {
        return flightTestSample.getFlightTestSample(SOURCE.pricing, service, quantity);
    }
}

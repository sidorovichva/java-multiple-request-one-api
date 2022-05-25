package com.vs.javamultiplerequestoneapi.service;

import com.vs.javamultiplerequestoneapi.DP;
import com.vs.javamultiplerequestoneapi.Fetcher;
import com.vs.javamultiplerequestoneapi.ferries.FerriesRequestPreparation;
import com.vs.javamultiplerequestoneapi.model.ferries.FerryTestResult;
import com.vs.javamultiplerequestoneapi.model.ferries.PricingFerryRequest;
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

    private final FerriesRequestPreparation ferriesRequestPreparation;
    private final Fetcher fetcher;

    public List<FerryTestResult> getFerryTestResult(int quantity) throws IOException {
        List<PricingFerryRequest> listOfRequests = ferriesRequestPreparation.getListOfFerryRequests(quantity);

        return listOfRequests.stream()
                .map(request -> FerryTestResult.builder()
                                .request(request)
                                .risk(fetcher.getPricingResponse(DP.Ferry, request).getProbability().get(0))
                                .dateTime(LocalDate.now())
                                .build())
                .filter(result -> {
                    if (result.getRisk() == 0) {
                        log.warn("result was filtered: {}", result);
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public List<Double> getRiskByOneRequest(PricingFerryRequest request) {
        return fetcher.getPricingResponse(DP.Ferry, request).getProbability();
    }
}

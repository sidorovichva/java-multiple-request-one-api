package com.vs.javamultiplerequestoneapi.fetchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.models.responses.pricing.PricingResponse;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightDetailsFetcher {

    private final ObjectMapper objectMapper;

    public PricingResponse getFlightDetailOtaResponse(
            DP dp,
            PricingFerryRequest pricingFerryRequest
    ) {
        HttpClient client = HttpClient.newHttpClient();

        String message = "";
        try {
            message = objectMapper.writeValueAsString(pricingFerryRequest);
        } catch (JsonProcessingException e) {
            log.error("Error while formatting request body: {}", pricingFerryRequest.toString());
            return new PricingResponse();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .header("Content-type", "application/json")
                .uri(URI.create(dp.getLink()))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Error while receiving response from the pricing server: {}", request.toString());
            return new PricingResponse();
        }

        String responseBody = response.body()
                .replaceAll("^\\[", "")
                .replaceAll("]$", "");

        PricingResponse pricingResponse;
        try {
            pricingResponse = objectMapper.readValue(responseBody, PricingResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing pricing server response: {}", responseBody);
            return new PricingResponse();
        }

        return pricingResponse;
    }
}

package com.vs.javamultiplerequestoneapi.fetchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import com.vs.javamultiplerequestoneapi.models.requests.flights.PricingFlightsRequest;
import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
import com.vs.javamultiplerequestoneapi.models.responses.pricing.PricingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class PricingResponseFetcher {

    private final ObjectMapper objectMapper;

    public PricingResponse getPricingResponseForFerry(
            String link,
            PricingFerryRequest pricingFerryRequest
    ) {
        HttpClient client = HttpClient.newHttpClient();

        String message = "";
        try {
            message = objectMapper.writeValueAsString(pricingFerryRequest);
        } catch (JsonProcessingException e) {
            log.error("Error while formatting request body: {}", pricingFerryRequest.toString());
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(message))
                .header("Content-type", "application/json")
                .uri(URI.create(link))
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Error while receiving response from the pricing server: {}", request.toString());
            return null;
        }

        String responseBody = response.body()
                .replaceAll("^\\[", "")
                .replaceAll("]$", "");

        PricingResponse pricingResponse;
        try {
            pricingResponse = objectMapper.readValue(responseBody, PricingResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing pricing server response: {}", responseBody);
            return null;
        }

        return pricingResponse;
    }

    public PricingResponse getPricingResponseForFlight(
            String link,
            PricingFlightsRequest pricingFlightsRequest
    ) {
        HttpClient client = HttpClient.newHttpClient();

        String message = "";
        try {
            message = objectMapper.writeValueAsString(pricingFlightsRequest);
        } catch (JsonProcessingException e) {
            log.error("Error while formatting request body: {}", pricingFlightsRequest.toString());
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(message))
                .header("Content-type", "application/json")
                .uri(URI.create(link))
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Error while receiving response from the pricing server: {}", request.toString());
            return null;
        }

        String responseBody = response.body()
                .replaceAll("^\\[", "")
                .replaceAll("]$", "");

        PricingResponse pricingResponse;
        try {
            pricingResponse = objectMapper.readValue(responseBody, PricingResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing pricing server response: {}", responseBody);
            return null;
        }

        return pricingResponse;
    }

    public PricingResponse getPricingResponseForWeather(
            PricingWXRequest pricingWeatherRequest,
            String product,
            String link
    ) {
        HttpClient client = HttpClient.newHttpClient();

        String message = "";
        try {
            message = objectMapper.writeValueAsString(pricingWeatherRequest);
        } catch (JsonProcessingException e) {
            log.error("Error while formatting request body: {}", pricingWeatherRequest.toString());
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(message))
                .header("Content-type", "application/json")
                .uri(URI.create(link + product))
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Error while receiving response from the pricing server: {}", request.toString());
            return null;
        }

        String responseBody = response.body()
                .replaceAll("^\\[", "")
                .replaceAll("]$", "");

        PricingResponse pricingResponse;
        try {
            pricingResponse = objectMapper.readValue(responseBody, PricingResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing pricing server response: {}", responseBody);
            return null;
        }

        return pricingResponse;
    }
}

package com.vs.javamultiplerequestoneapi.fetchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.javamultiplerequestoneapi.enums.SOURCE;
import com.vs.javamultiplerequestoneapi.models.requests.flights.FlightDetailsRequest;
import com.vs.javamultiplerequestoneapi.models.responses.ota.DetailedFlightDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightDetailsFetcher {

    private final ObjectMapper objectMapper;
    private static final Field[] classFields = FlightDetailsRequest.class.getDeclaredFields();
    private static final String start = "?";
    private static final String next = "&";
    private static final String equals = "=";

    public DetailedFlightDTO getFlightDetailOtaResponse(
            SOURCE source,
            FlightDetailsRequest flightDetailsRequest
    ) {
        HttpClient client = HttpClient.newHttpClient();

        String message = "";
        try {
            message = objectMapper.writeValueAsString(flightDetailsRequest);
        } catch (JsonProcessingException e) {
            log.error("Error while formatting request body: {}", flightDetailsRequest.toString());
            return null;
        }

        StringBuilder requestParameters = new StringBuilder();
        requestParameters
                .append(start).append(classFields[0].getName()).append(equals).append(flightDetailsRequest.getFlightNumber())
                .append(next).append(classFields[1].getName()).append(equals).append(flightDetailsRequest.getAirlineId())
                .append(next).append(classFields[2].getName()).append(equals).append(flightDetailsRequest.getDepartingDate())
                .append(next).append(classFields[3].getName()).append(equals).append(flightDetailsRequest.getOriginAirportId())
                .append(next).append(classFields[4].getName()).append(equals).append(flightDetailsRequest.getDestinationAirportId());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Content-type", "application/json")
                .uri(URI.create(source.getLink() + requestParameters))
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Error while receiving response from ota server: {}", request.toString());
            return null;
        }

        String responseBody = response.body()
                .replaceAll("^\\[", "")
                .replaceAll("]$", "");

        DetailedFlightDTO detailedFlightDTO;
        try {
            detailedFlightDTO = objectMapper.readValue(responseBody, DetailedFlightDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing ota server response: {}", responseBody);
            return null;
        }

        if (detailedFlightDTO.getFlight() == null)
            return null;

        return detailedFlightDTO;
    }
}

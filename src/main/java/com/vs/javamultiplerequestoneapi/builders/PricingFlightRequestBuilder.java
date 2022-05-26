package com.vs.javamultiplerequestoneapi.builders;

import com.vs.javamultiplerequestoneapi.models.requests.flights.Flight;
import com.vs.javamultiplerequestoneapi.models.requests.flights.FlightSub;
import com.vs.javamultiplerequestoneapi.models.requests.flights.PricingFlightsRequest;
import com.vs.javamultiplerequestoneapi.models.responses.ota.DetailedFlightDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricingFlightRequestBuilder extends RequestBuilder {

    public PricingFlightsRequest buildPricingFlightRequest(DetailedFlightDTO dto) {

        FlightSub departure = FlightSub.builder()
                .airport(dto.getDepartureAirport().getCode())
                .instant(dto.getDepartureDateTime())
                .datetime(dto.getDepartureDateTime().toString())
                .localdatetime(dto.getLocalDepartureDateTime())
                .build();

        FlightSub arrival = FlightSub.builder()
                .airport(dto.getArrivalAirport().getCode())
                .instant(dto.getArrivalDateTime())
                .datetime(dto.getArrivalDateTime().toString())
                .localdatetime(dto.getLocalArrivalDateTime())
                .build();

        Flight flight = Flight.builder()
                .id(dto.getFlightDate() + dto.getDepartureAirport().getCode() + dto.getArrivalAirport().getCode() + dto.getFlightId())
                .number(Integer.parseInt(dto.getFlightId().replaceAll("^[A-Z]+", "")))
                .operator(dto.getAirlineId())
                .marketer(dto.getFlightId().replaceAll("\\d+$", ""))
                .departure(departure)
                .arrival(arrival)
                .build();

        return PricingFlightsRequest.builder()
                .flight(List.of(flight))
                .alloweddelay(List.of(60, 120))
                .givenFlightNotCancelled(List.of(true, true))
                .build();
    }
}

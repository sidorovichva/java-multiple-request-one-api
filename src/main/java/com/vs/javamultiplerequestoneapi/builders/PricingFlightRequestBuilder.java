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
                .number(dto.getFlight().getFlightNumber())
                .operator(dto.getAirlineId())
                .marketer(dto.getFlight().getAirlineId())
                .departure(departure)
                .arrival(arrival)
                .build();

        return PricingFlightsRequest.builder()
                .flight(List.of(flight))
                .alloweddelay(List.of(60, 120, 180, 10000))
                .givenFlightNotCancelled(List.of(true, true, true, false))
                .build();
    }
}

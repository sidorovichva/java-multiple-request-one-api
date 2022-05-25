package com.vs.javamultiplerequestoneapi.models.responses.ota;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedFlightDTO {

    private String flightId;
    private String airlineId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate flightDate;
    private DtoFlight flight;
    private AirLineCompanyDTO airline;
    private CityDTO departureCity;
    private CityDTO arrivalCity;
    private AirportDTO departureAirport;
    private AirportDTO arrivalAirport;
    private Boolean departedFromEU;
    private Boolean arrivedToEU;
    private Instant departureDateTime;
    private Instant arrivalDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime localDepartureDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime localArrivalDateTime;
    private FlightFilterData flightFilterData;
}

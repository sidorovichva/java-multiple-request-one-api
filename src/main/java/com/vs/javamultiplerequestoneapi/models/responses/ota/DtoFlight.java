package com.vs.javamultiplerequestoneapi.models.responses.ota;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoFlight extends DtoBase {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") private LocalTime flightTime = null;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") private LocalTime flightLanding = null;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer departureUTCOffset;
    private Integer landingUTCOffset;
    private int flightNumber;
    private String flightDuration = "0";
    private int flightDuharionHours = 0;
    private int flightDuharionMinutes = 0;
    private int tempFlightCost = 400;
    private String airlineId;
    private String originAirportId;
    private String departureCityCode;
    private String departureCountryCode;
    private String destinationAirportId;
    private String arrivalCityCode;
    private String arrivalCountryCode;
    private DayOfWeek dayOfWeek;
    private String daysOfOperation = "1234567";
    private String arrivalDayMarker;
    private String generalAircraftTypeCode;
    private String specificAircraftTypeCode;
    private String terminalFrom = "";
    private String terminalTo = "";
    private String sharedAirlineDesignator;
    private OperatingMarker operatingMarker;
    private AircraftBody aircraftBody;
    private String comment;
    private String duplicateCarrier1;
    private Integer duplicateFlight1;
}

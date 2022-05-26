package com.vs.javamultiplerequestoneapi.builders;

import com.vs.javamultiplerequestoneapi.models.requests.flights.FlightDetailsRequest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlightDetailsRequestBuilder extends RequestBuilder {

    private static final String file = "files/flights2022-05.csv";

    public List<FlightDetailsRequest> buildListOfFlightDetailsRequests(int quantity) throws IOException {

        List<FlightDetailsRequest> list = new ArrayList<>();
        int counter = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while (((line = br.readLine()) != null) && (counter <= quantity)) {

            if (counter == 0) {
                counter += 1;
                continue;
            }

            String[] cols = line.split(separator);

            list.add(FlightDetailsRequest.builder()
                    .flightNumber(Integer.parseInt(cols[3]))
                    .airlineId(cols[6])
                    .departingDate(cols[4].substring(0, 10))
                    .originAirportId(cols[0])
                    .destinationAirportId(cols[1])
                    .build());

            counter += 1;
        }

        return list;
    }
}

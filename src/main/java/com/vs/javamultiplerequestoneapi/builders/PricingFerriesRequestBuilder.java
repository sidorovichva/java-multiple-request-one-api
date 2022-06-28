package com.vs.javamultiplerequestoneapi.builders;

import com.vs.javamultiplerequestoneapi.models.requests.ferries.Disruption;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.FerryTripEntry;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryItinerary;
import com.vs.javamultiplerequestoneapi.models.requests.ferries.PricingFerryRequest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PricingFerriesRequestBuilder extends RequestBuilder {

    private static final String file = "files/2022-2.csv";
    private static final boolean givenNotCancelled = true;
    private static final String countryPrefix = "GR";

    public List<PricingFerryRequest> getListOfFerryRequests(int quantity, int delay) throws IOException {

        List<PricingFerryRequest> list = new ArrayList<>();
        int counter = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while (((line = br.readLine()) != null) && (counter <= quantity)) {

            if (counter == 0) {
                counter += 1;
                continue;
            }

            String[] cols = line.split(separator);

            if ( ! cols[6].startsWith(countryPrefix) || ! cols[9].startsWith(countryPrefix))
                continue;

            FerryTripEntry departure = FerryTripEntry.builder()
                    .port(cols[7])
                    .localDateTime(
                            LocalDateTime.of(
                                    Integer.parseInt(cols[0].substring(0, 4)),
                                    Integer.parseInt(cols[0].substring(4, 6)),
                                    Integer.parseInt(cols[0].substring(6, 8)),
                                    Integer.parseInt(cols[1].substring(0, 2)),
                                    Integer.parseInt(cols[1].substring(2, 4))
                                    )
                    )
                    .build();

            FerryTripEntry arrival = FerryTripEntry.builder()
                    .port(cols[10])
                    .localDateTime(
                            LocalDateTime.of(
                                    Integer.parseInt(cols[2].substring(0, 4)),
                                    Integer.parseInt(cols[2].substring(4, 6)),
                                    Integer.parseInt(cols[2].substring(6, 8)),
                                    Integer.parseInt(cols[3].substring(0, 2)),
                                    Integer.parseInt(cols[3].substring(2, 4))
                            )
                    )
                    .build();

            Disruption disruption = Disruption.builder()
                    .delay((double) delay)
                    .givenNotCancelled(givenNotCancelled)
                    .build();

            PricingFerryItinerary itinerary = PricingFerryItinerary.builder()
                    .departure(departure)
                    .arrival(arrival)
                    .id(cols[7] + "|" + cols[10])
                    .operator(cols[14])
                    .build();

            list.add(PricingFerryRequest.builder()
                    .disruptions(List.of(disruption))
                    .itineraries(List.of(itinerary))
                    .build());

            counter += 1;
        }

        return list;
    }
}

package com.vs.javamultiplerequestoneapi.builders;

import com.vs.javamultiplerequestoneapi.models.requests.weather.PricingWXRequest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class PricingWeatherRequestBuilder extends RequestBuilder {

    private static final String file = "files/weather-stations.csv";

    public List<PricingWXRequest> getListOfWeatherRequests(int quantity) throws IOException {

        List<PricingWXRequest> list = new ArrayList<>();
        int counter = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while (((line = br.readLine()) != null) && (counter <= quantity)) {

            if (counter == 0) {
                counter += 1;
                continue;
            }

            String[] cols = line.split(separator);

            PricingWXRequest.Location location = PricingWXRequest.Location.builder()
                    .latitude(Double.parseDouble(cols[1]))
                    .longitude(Double.parseDouble(cols[2]))
                    .elevation(Double.parseDouble(cols[3]))
                    .build();

            PricingWXRequest.WXEvent event = PricingWXRequest.WXEvent.builder()
                    .id(cols[0])
                    .date(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now().plusDays(Integer.parseInt(cols[4].substring(0, 2)))))
                    .location(location)
                    .build();

            list.add(PricingWXRequest.builder()
                    .amount(List.of(10.))
                    .days(List.of("3"))
                    .events(List.of(event))
                    .build());

            counter += 1;
        }

        return list;
    }
}

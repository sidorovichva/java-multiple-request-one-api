package com.vs.javamultiplerequestoneapi.models.requests.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PricingWXRequest {
    private List<Double> amount;
    private List<String> days;
    private List<WXEvent> events;

    public PricingWXRequest(Integer rainyDayThreshold,
                            Integer daysToMeasure,
                            Integer period,
                            Double latitude,
                            Double longitude,
                            Double elevation,
                            LocalDate fromDate
    ) {
        days = new ArrayList<>();
        days.add(String.format("%d/%d", daysToMeasure, period));
        events = new ArrayList<>();
        events.add(new WXEvent(longitude, latitude, elevation, fromDate, "index"));
        if (rainyDayThreshold != null) {
            amount = new ArrayList<>();
            amount.add(rainyDayThreshold.doubleValue());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private Double latitude;
        private Double longitude;
        private Double elevation;
    }

    @Data
    @NoArgsConstructor
    public static class WXEvent {
        private String id;
        private String date;
        private Location location;

        public WXEvent(Double longitude, Double latitude, Double elevation, LocalDate fromDate, String id) {
            this.location = new Location(longitude, latitude, elevation);
            this.date = DateTimeFormatter.ofPattern("yyyyMMdd").format(fromDate);
            this.id = id;

        }
    }
}
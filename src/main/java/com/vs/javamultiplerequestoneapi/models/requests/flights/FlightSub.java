package com.vs.javamultiplerequestoneapi.models.requests.flights;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSub {

    private String airport;
    private Instant instant;
    private String datetime;
    private LocalDateTime localdatetime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getLocaldatetime() {
        return localdatetime;
    }
}

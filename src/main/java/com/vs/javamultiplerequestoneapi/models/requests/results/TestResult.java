package com.vs.javamultiplerequestoneapi.models.requests.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResult {

    private List<String> id;
    private List<LocalDateTime> date;
    private List<Integer> delay;
    private List<Boolean> notCancelled;
    private List<Double> risk;
}

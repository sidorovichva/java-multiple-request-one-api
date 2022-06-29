package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.models.results.TransportationTestResult;
import com.vs.javamultiplerequestoneapi.models.results.WeatherTestResult;
import com.vs.javamultiplerequestoneapi.repositories.TransportationTestResultRepository;
import com.vs.javamultiplerequestoneapi.repositories.WeatherTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultService {

    private final TransportationTestResultRepository transportationTestResultRepository;
    private final WeatherTestResultRepository weatherTestResultRepository;

    public List<TransportationTestResult> getAllTransportTestResultsInLastXDays(int days) {
        return transportationTestResultRepository.findByDateAfter(LocalDateTime.now().minusDays(days).withHour(0).withMinute(0));
    }

    public List<WeatherTestResult> getAllWeatherTestResultsInLastXDays(int days) {
        return weatherTestResultRepository.findByDateAfter(LocalDateTime.now().minusDays(days).withHour(0).withMinute(0));
    }

    public List<TransportationTestResult> getAllSingleTestResultsByDelayInLastXDays(int delay, int days) {
        return transportationTestResultRepository.findByDelayAndDateAfter(delay, LocalDateTime.now().minusDays(days).withHour(1).withMinute(0));
    }
}

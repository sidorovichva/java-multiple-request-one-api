package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.repositories.SingleTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleTestResultService {

    private final SingleTestResultRepository repository;

    public List<SingleTestResult> getAllSingleTestResultsInLastXDays(int days) {
        return repository.findByDateAfter(LocalDateTime.now().minusDays(days).withHour(0).withMinute(0));
    }

    public List<SingleTestResult> getAllSingleTestResultsByDelayInLastXDays(int delay, int days) {
        return repository.findByDelayAndDateAfter(delay, LocalDateTime.now().minusDays(days).withHour(1).withMinute(0));
    }
}

package com.vs.javamultiplerequestoneapi.repositories;

import com.vs.javamultiplerequestoneapi.models.results.TransportationTestResult;
import com.vs.javamultiplerequestoneapi.models.results.TestResultCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransportationTestResultRepository
        extends JpaRepository<TransportationTestResult, TestResultCompositeKey> {

    List<TransportationTestResult> findByDateAfter(LocalDateTime date);

    List<TransportationTestResult> findByDelayAndDateAfter(int delay, LocalDateTime date);
}

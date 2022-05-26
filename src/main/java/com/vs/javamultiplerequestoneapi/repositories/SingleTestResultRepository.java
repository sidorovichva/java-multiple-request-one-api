package com.vs.javamultiplerequestoneapi.repositories;

import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResultCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SingleTestResultRepository extends JpaRepository<SingleTestResult, SingleTestResultCompositeKey> {

    List<SingleTestResult> findByDateAfter(LocalDateTime date);

    List<SingleTestResult> findByDelayAndDateAfter(int delay, LocalDateTime date);
}

package com.vs.javamultiplerequestoneapi.repositories;

import com.vs.javamultiplerequestoneapi.models.results.TestResultCompositeKey;
import com.vs.javamultiplerequestoneapi.models.results.WeatherTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherTestResultRepository extends JpaRepository<WeatherTestResult, TestResultCompositeKey> {

    List<WeatherTestResult> findByDateAfter(LocalDateTime date);

    List<WeatherTestResult> findByAmountAndDateAfter(int amount, LocalDateTime date);

    List<WeatherTestResult> findByDaysAndDateAfter(int days, LocalDateTime date);
}

package com.assignment.holidaykeeper.service;

import com.assignment.holidaykeeper.domain.entity.Country;
import com.assignment.holidaykeeper.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class HolidayBatchProcessor {

    private final CountryRepository countryRepository;
    private final TaskExecutor taskExecutor;

    public void process(HolidayProcessor processor) {
        List<Country> countries = countryRepository.findAll();

        List<CompletableFuture<Void>> futures = countries.stream()
                .map(country -> CompletableFuture.runAsync(() -> {
                    for (int year = 2020; year <= 2025; year++) {
                        processor.process(country.getCountryCode(), year);
                    }
                }, taskExecutor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    @FunctionalInterface
    public interface HolidayProcessor {
        void process(String countryCode, int year);
    }
}

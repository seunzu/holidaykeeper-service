package com.assignment.holidaykeeper.service;

import com.assignment.holidaykeeper.domain.dto.HolidayResponse;
import com.assignment.holidaykeeper.domain.dto.HolidaySearchCondition;
import com.assignment.holidaykeeper.domain.entity.Holiday;
import com.assignment.holidaykeeper.repository.HolidayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final HolidayApiClient holidayApiClient;
    private final HolidayBatchProcessor batchProcessor;
    private final CountryService countryService;

    @Override
    public void loadHolidays(String countryCode, int year) {
        HolidayResponse[] holidays = holidayApiClient.fetchHolidays(countryCode, year);

        if (holidays != null) {
            List<Holiday> holidayList = Arrays.stream(holidays)
                    .map(HolidayResponse::toEntity)
                    .map(holiday -> holiday.toBuilder()
                            .year(holiday.getDate().getYear())
                            .build())
                    .toList();

            holidayRepository.saveAll(holidayList);
        }
    }

    @Override
    public void refreshHolidays(String countryCode, int year) {
        HolidayResponse[] holidays = holidayApiClient.fetchHolidays(countryCode, year);

        if (holidays != null) {
            Arrays.stream(holidays)
                    .map(HolidayResponse::toEntity)
                    .map(holiday -> holiday.toBuilder()
                            .year(holiday.getDate().getYear())
                            .build())
                    .filter(holiday -> holiday.getDate().getYear() == year)
                    .forEach(holidayRepository::upsertHoliday);
        }
    }

    @Override
    public void loadHolidaysOnly() {
        Set<Integer> targetYears = IntStream.rangeClosed(2020, 2025)
                .boxed()
                .collect(Collectors.toSet());

        batchProcessor.processForYears(targetYears, this::loadHolidays);
    }

    @Override
    public void syncHolidaysOnly() {
        int currentYear = Year.now().getValue();
        int previousYear = currentYear - 1;

        Set<Integer> yearsToSync = Set.of(previousYear, currentYear);
        batchProcessor.processForYears(yearsToSync, this::refreshHolidays);
    }

    @Override
    public void loadAllData() {
        countryService.loadCountries();
        loadHolidaysOnly();
    }

    @Override
    @Transactional
    public void deleteHolidays(String countryCode, int year) {
        holidayRepository.deleteByCountryCodeAndYear(countryCode, year);
    }

    @Override
    public Page<Holiday> searchHolidays(HolidaySearchCondition condition, Pageable pageable) {
        return holidayRepository.searchHolidays(condition, pageable);
    }

    @Override
    public Boolean isTodayHoliday(String countryCode) {
        List<Holiday> holidays = holidayRepository.findByCountryCodeAndDate(countryCode, LocalDate.now());
        return holidays != null && !holidays.isEmpty();
    }
}

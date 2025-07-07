package com.assignment.holidaykeeper.domain.dto;

import java.time.LocalDate;

public record HolidaySearchCondition(
        String countryCode,
        Integer year,
        LocalDate fromDate,
        LocalDate toDate,
        String type
) { }


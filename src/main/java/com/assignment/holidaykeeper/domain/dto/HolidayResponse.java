package com.assignment.holidaykeeper.domain.dto;

import com.assignment.holidaykeeper.domain.entity.Holiday;

import java.time.LocalDate;
import java.util.List;

public record HolidayResponse(
        String date,
        String localName,
        String name,
        String countryCode,
        boolean fixed,
        boolean global,
        List<String> counties,
        String launchYear,
        List<String> types
) {
    public static HolidayResponse from(Holiday holiday) {
        return new HolidayResponse(
                holiday.getDate().toString(),
                holiday.getLocalName(),
                holiday.getName(),
                holiday.getCountryCode(),
                holiday.isFixed(),
                holiday.isGlobal(),
                holiday.getCounties() != null ? List.of(holiday.getCounties().split(",")) : null,
                holiday.getLaunchYear(),
               holiday.getTypes() != null ? List.of(holiday.getTypes().split(",")) : null
        );
    }

    public Holiday toEntity() {
        return Holiday.builder()
                .date(LocalDate.parse(date))
                .localName(localName)
                .name(name)
                .countryCode(countryCode)
                .fixed(fixed)
                .global(global)
                .counties(counties != null ? String.join(",", counties) : null)
                .launchYear(launchYear)
                .types(types != null && !types.isEmpty() ? types.get(0) : null)
                .build();
    }
}


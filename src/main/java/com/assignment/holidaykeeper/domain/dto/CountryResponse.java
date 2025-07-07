package com.assignment.holidaykeeper.domain.dto;

import com.assignment.holidaykeeper.domain.entity.Country;

public record CountryResponse(
        String countryCode,
        String name
) {

    public Country toEntity() {
        return new Country(countryCode, name);
    }
}
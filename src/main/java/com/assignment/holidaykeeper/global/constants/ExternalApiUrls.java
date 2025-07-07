package com.assignment.holidaykeeper.global.constants;

public final class ExternalApiUrls {

    public static final String BASE_URL = "https://date.nager.at/api/v3";
    public static final String AVAILABLE_COUNTRIES = BASE_URL + "/AvailableCountries";
    public static final String PUBLIC_HOLIDAYS = BASE_URL + "/PublicHolidays/%d/%s";

    private ExternalApiUrls() {
    }
}

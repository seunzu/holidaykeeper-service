package com.assignment.holidaykeeper.service;

import com.assignment.holidaykeeper.domain.dto.CountryResponse;
import com.assignment.holidaykeeper.domain.entity.Country;
import com.assignment.holidaykeeper.global.constants.ExternalApiUrls;
import com.assignment.holidaykeeper.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final RestTemplate restTemplate;

    @Override
    public void loadCountries() {
        String url = ExternalApiUrls.AVAILABLE_COUNTRIES;
        CountryResponse[] responses = restTemplate.getForObject(url, CountryResponse[].class);

        if (responses != null) {
            List<Country> countries = Arrays.stream(responses)
                    .map(CountryResponse::toEntity)
                    .toList();

            countryRepository.saveAll(countries);
        }
    }
}

package com.assignment.holidaykeeper.service;

import com.assignment.holidaykeeper.domain.dto.HolidayResponse;
import com.assignment.holidaykeeper.global.constants.ExternalApiUrls;
import com.assignment.holidaykeeper.global.exception.ApplicationException;
import com.assignment.holidaykeeper.global.exception.errorcode.CommonErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class HolidayApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HolidayResponse[] fetchHolidays(String countryCode, int year) {
        String url = String.format(ExternalApiUrls.PUBLIC_HOLIDAYS, year, countryCode);

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();

            return objectMapper.readValue(responseBody, HolidayResponse[].class);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(CommonErrorCode.INTERNAL_SERVER_ERROR, e);
        } catch (Exception e) {
            throw new ApplicationException(CommonErrorCode.EXTERNAL_API_ERROR, e);
        }
    }
}

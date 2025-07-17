package com.assignment.holidaykeeper.controller;

import com.assignment.holidaykeeper.domain.dto.HolidayResponse;
import com.assignment.holidaykeeper.domain.dto.HolidaySearchCondition;
import com.assignment.holidaykeeper.domain.dto.PageResponse;
import com.assignment.holidaykeeper.global.response.ApiResponse;
import com.assignment.holidaykeeper.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    // 전체 데이터 적재 (초기화)
    @PostMapping("/v1/data-load")
    public ApiResponse<Void> loadAllData() {
        holidayService.loadAllData();
        return ApiResponse.ok();
    }

    // 재동기화
    @PostMapping("/v1/holidays/refresh")
    public ApiResponse<Void> refreshHolidays(
            @RequestParam("countryCode") String countryCode,
            @RequestParam("year") int year) {
        holidayService.refreshHolidays(countryCode, year);
        return ApiResponse.ok();
    }

    // 삭제
    @DeleteMapping("/v1/holidays")
    public ApiResponse<Void> deleteHolidays(
            @RequestParam("countryCode") String countryCode,
            @RequestParam("year") int year) {
        holidayService.deleteHolidays(countryCode, year);
        return ApiResponse.ok();
    }

    // 검색
    @GetMapping("/v1/holidays")
    public ApiResponse<PageResponse<HolidayResponse>> searchHolidays(
            @ModelAttribute HolidaySearchCondition condition,
            Pageable pageable
    ) {
        Page<HolidayResponse> page = holidayService.searchHolidays(condition, pageable)
                .map(HolidayResponse::from);
        return ApiResponse.ok(PageResponse.from(page));
    }

    // 오늘이 공휴일??
    @GetMapping("/v1/holidays/isTodayHoliday/{countryCode}")
    public ApiResponse<Boolean> isTodayHoliday(@PathVariable("countryCode") String countryCode) {
        Boolean todayHoliday = holidayService.isTodayHoliday(countryCode);
        return ApiResponse.ok(todayHoliday);
    }
}
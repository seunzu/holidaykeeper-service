package com.assignment.holidaykeeper.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HolidaySyncScheduler {

    private final HolidayService holidayService;

    @Scheduled(cron = "0 0 1 2 1 *", zone = "Asia/Seoul")
    @CircuitBreaker(name = "holidayScheduler", fallbackMethod = "fallback")
    @Retry(name = "holidayScheduler", fallbackMethod = "fallback")
    public void syncHolidayData() {
        holidayService.syncHolidaysOnly();
        System.out.println("전년도·금년도 데이터 자동 동기화");
    }

    public void fallback(Throwable t) {
        System.out.println("Fallback : " + t.getMessage());
    }
}

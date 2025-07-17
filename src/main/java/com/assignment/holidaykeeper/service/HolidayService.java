package com.assignment.holidaykeeper.service;

import com.assignment.holidaykeeper.domain.dto.HolidaySearchCondition;
import com.assignment.holidaykeeper.domain.entity.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HolidayService {

    void loadHolidays(String countryCode, int year);
    void refreshHolidays(String countryCode, int year);
    void loadHolidaysOnly();
    void syncHolidaysOnly();
    void loadAllData();
    void deleteHolidays(String countryCode, int year);
    Page<Holiday> searchHolidays(HolidaySearchCondition condition, Pageable pageable);
    Boolean isTodayHoliday(String countryCode);
}

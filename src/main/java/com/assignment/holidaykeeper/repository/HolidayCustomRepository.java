package com.assignment.holidaykeeper.repository;

import com.assignment.holidaykeeper.domain.dto.HolidaySearchCondition;
import com.assignment.holidaykeeper.domain.entity.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HolidayCustomRepository {

    void upsertHoliday(Holiday holiday);
    Page<Holiday> searchHolidays(HolidaySearchCondition condition, Pageable pageable);
}

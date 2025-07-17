package com.assignment.holidaykeeper.repository;

import com.assignment.holidaykeeper.domain.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long>, HolidayCustomRepository {

    @Modifying
    @Query("DELETE FROM Holiday h WHERE h.countryCode = :countryCode AND h.year = :year")
    void deleteByCountryCodeAndYear(@Param("countryCode") String countryCode, @Param("year") int year);

    List<Holiday> findByCountryCodeAndDate(String countryCode, LocalDate date);
}

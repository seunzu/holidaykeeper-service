package com.assignment.holidaykeeper.repository;

import com.assignment.holidaykeeper.domain.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}

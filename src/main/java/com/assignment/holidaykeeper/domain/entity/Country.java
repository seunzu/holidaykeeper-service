package com.assignment.holidaykeeper.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "countries")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @Column(nullable = false, unique = true)
    private String countryCode;
    @Column(nullable = false)
    private String name;
}

package com.assignment.holidaykeeper.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String localName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String countryCode;
    private boolean fixed;
    private boolean global;
    @Column(columnDefinition = "TEXT")
    private String counties;
    private String launchYear;
    private String types;
    @Column(name = "`year`", nullable = false)
    private int year;
}

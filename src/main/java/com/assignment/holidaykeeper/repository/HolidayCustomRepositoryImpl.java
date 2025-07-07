package com.assignment.holidaykeeper.repository;

import com.assignment.holidaykeeper.domain.dto.HolidaySearchCondition;
import com.assignment.holidaykeeper.domain.entity.Holiday;
import com.assignment.holidaykeeper.domain.entity.QHoliday;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HolidayCustomRepositoryImpl implements HolidayCustomRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public void upsertHoliday(Holiday holiday) {
        entityManager.createNativeQuery("""
            MERGE INTO holidays (date, country_code, "year", local_name, name, fixed, global, counties, launch_year, types)
            KEY (date, country_code)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """)
                .setParameter(1, holiday.getDate())
                .setParameter(2, holiday.getCountryCode())
                .setParameter(3, holiday.getYear())
                .setParameter(4, holiday.getLocalName())
                .setParameter(5, holiday.getName())
                .setParameter(6, holiday.isFixed())
                .setParameter(7, holiday.isGlobal())
                .setParameter(8, holiday.getCounties())
                .setParameter(9, holiday.getLaunchYear())
                .setParameter(10, holiday.getTypes())
                .executeUpdate();
    }

    @Override
    public Page<Holiday> searchHolidays(HolidaySearchCondition condition, Pageable pageable) {
        QHoliday holiday = QHoliday.holiday;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.countryCode() != null) {
            builder.and(holiday.countryCode.eq(condition.countryCode()));
        }
        if (condition.year() != null) {
            builder.and(holiday.year.eq(condition.year()));
        }
        if (condition.fromDate() != null && condition.toDate() != null) {
            builder.and(holiday.date.between(condition.fromDate(), condition.toDate()));
        }
        if (condition.type() != null) {
            builder.and(holiday.types.containsIgnoreCase(condition.type()));
        }

        List<Holiday> content = jpaQueryFactory
                .selectFrom(holiday)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(holiday.count())
                .from(holiday)
                .where(builder)
                .fetchOne();

        long total = (count != null) ? count : 0L;

        return new PageImpl<>(content, pageable, total);
    }
}

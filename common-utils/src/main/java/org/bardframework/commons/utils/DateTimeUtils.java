package org.bardframework.commons.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
@UtilityClass
@Slf4j
public class DateTimeUtils {

    public static final long ONE_SECOND_MILLS = 1000;
    public static final long ONE_MINUTE_MILLS = 60 * ONE_SECOND_MILLS;
    public static final long ONE_HOUR_MILLS = 60 * ONE_MINUTE_MILLS;
    public static final long ONE_DAY_MILLS = 24 * ONE_HOUR_MILLS;
    public static final long ONE_WEEK_MILLS = 7 * ONE_DAY_MILLS;
    public static final long ONE_MONTH_MILLS = 30 * ONE_DAY_MILLS;
    public static final long ONE_YEAR_MILLS = 12 * ONE_MONTH_MILLS;

    public static long getNowUtc() {
        return Instant.now().toEpochMilli();
    }

    public static long getTodayUtc() {
        return Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli();
    }

    public static long toEpochMills(LocalDateTime dateTime) {
        return DateTimeUtils.toEpochMills(dateTime, ZoneOffset.UTC);
    }

    public static long toEpochMills(LocalDateTime dateTime, ZoneOffset offset) {
        return dateTime.toInstant(offset).toEpochMilli();
    }

    public static LocalDateTime fromEpochMills(long dateTimeAsMills) {
        return DateTimeUtils.fromEpochMills(dateTimeAsMills, ZoneOffset.UTC);
    }

    public static LocalDate dateFromEpochMills(long dateAsMills) {
        return Instant.ofEpochMilli(dateAsMills).atZone(ZoneOffset.UTC).toLocalDate();
    }

    /**
     * Obtains an instance of {@code LocalDateTime} using milliseconds from the epoch of 1970-01-01T00:00:00Z.
     *
     * @param dateTimeAsMills the number of milliseconds after 1970-01-01T00:00:00Z
     * @param zone            the zone
     * @return a LocalDateTime, not null
     */
    public static LocalDateTime fromEpochMills(long dateTimeAsMills, ZoneId zone) {
        if (null == zone) {
            throw new IllegalArgumentException("null zone not acceptable.");
        }
        return Instant.ofEpochMilli(dateTimeAsMills).atZone(zone).toLocalDateTime();
    }

    public static long diffDays(long startDateEpochMills, long endDateEpochMills) {
        return ChronoUnit.DAYS.between(DateTimeUtils.fromEpochMills(startDateEpochMills), DateTimeUtils.fromEpochMills(endDateEpochMills));
    }

    public static long leftDays(long startDateEpochMills) {
        return ChronoUnit.DAYS.between(DateTimeUtils.fromEpochMills(startDateEpochMills), LocalDateTime.now());
    }

    public static long remainDays(long endDateEpochMills) {
        return ChronoUnit.DAYS.between(LocalDateTime.now(), DateTimeUtils.fromEpochMills(endDateEpochMills));
    }

    /**
     * calculate milliseconds past from the epoch of 1970-01-01T00:00:00Z.
     *
     * @param date an instance of (@code LocalDate)
     * @return past milliseconds from 1970-01-01T00:00:00Z according (!@code date) at 23:59:59:99:999.
     * @throws IllegalArgumentException if null date passed to method.
     */
    public static long toEpochMills(LocalDate date) {
        if (null == date) {
            throw new IllegalArgumentException("null date not acceptable.");
        }
        return date.toEpochDay() * 24 * 60 * 60 * 1000L;
    }
}

package com.nc.core.common.utils;

import org.apache.tomcat.util.http.ConcurrentDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/2/11
 * @package: com.nc.core.common.utils
 */
public class DateUtil {
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATETIME_MINI = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final ConcurrentDateFormat DATETIME_FORMAT = new ConcurrentDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault(), TimeZone.getDefault());
    public static final ConcurrentDateFormat DATETIME_MINI_FORMAT = new ConcurrentDateFormat("yyyyMMddHHmmss", Locale.getDefault(), TimeZone.getDefault());
    public static final ConcurrentDateFormat DATE_FORMAT = new ConcurrentDateFormat("yyyy-MM-dd", Locale.getDefault(), TimeZone.getDefault());
    public static final ConcurrentDateFormat TIME_FORMAT = new ConcurrentDateFormat("HH:mm:ss", Locale.getDefault(), TimeZone.getDefault());

    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_MINI_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static String formatDateTime(TemporalAccessor temporal) {
        return DATETIME_FORMATTER.format(temporal);
    }

    public static String formatDateTimeMini(TemporalAccessor temporal) {
        return DATETIME_MINI_FORMATTER.format(temporal);
    }
    public static String formatDate(TemporalAccessor temporal) {
        return DATE_FORMATTER.format(temporal);
    }

    public static String formatTime(TemporalAccessor temporal) {
        return TIME_FORMATTER.format(temporal);
    }

    public static String format(TemporalAccessor temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }
}

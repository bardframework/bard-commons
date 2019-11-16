package org.bardframework.commons.jackson.converter;

import org.bardframework.jalalidate.JalaliDateTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
@Component
public class LocalDateTimeToStringConverter extends ConverterSerializer<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm");

    @Override
    public String convert(LocalDateTime value) {
        return JalaliDateTime.of(value).format(FORMATTER);
    }
}

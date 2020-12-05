package org.bardframework.commons.jackson.converter;

import org.bardframework.commons.utils.StringUtils;
import org.bardframework.time.LocalDateTimeJalali;

import java.time.LocalDateTime;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class JalaliDateTimeStringToLocalDateTimeConverter extends ConverterDeserializer<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String value) {
        return StringUtils.hasText(value) ? LocalDateTimeJalali.of(value).toLocalDateTime() : null;
    }
}

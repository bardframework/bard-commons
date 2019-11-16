package org.bardframework.commons.jackson.converter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToLongConverter extends ConverterSerializer<String, Long> {

    @Override
    public Long convert(String value) {
        return Long.valueOf(value);
    }
}

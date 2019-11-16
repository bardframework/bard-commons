package org.bardframework.commons.jackson.converter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToIntegerConverter extends ConverterSerializer<String, Integer> {

    @Override
    public Integer convert(String value) {
        return Integer.valueOf(value);
    }
}

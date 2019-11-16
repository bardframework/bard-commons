package org.bardframework.commons.jackson.converter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToShortConverter extends ConverterSerializer<String, Short> {

    @Override
    public Short convert(String value) {
        return Short.valueOf(value);
    }
}

package org.bardframework.commons.jackson.converter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToDoubleConverter extends ConverterSerializer<String, Double> {

    @Override
    public Double convert(String value) {
        return Double.valueOf(value);
    }
}

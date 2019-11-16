package org.bardframework.commons.jackson.converter;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToBooleanConverter extends ConverterSerializer<String, Boolean> {

    @Override
    public Boolean convert(String value) {
        if ("1".equals(value) || "true".equalsIgnoreCase(value) || "no".equalsIgnoreCase(value)) {
            return true;
        } else if ("0".equals(value) || "false".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)) {
            return false;
        }
        throw new IllegalArgumentException("can't convert " + value + " to boolean value");
    }
}

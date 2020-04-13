package org.bardframework.commons.jackson.converter;

import org.bardframework.commons.utils.AssertionUtils;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class StringToEnumSerializer<ENUM extends Enum<ENUM>> extends ConverterSerializer<String, ENUM> {

    private Class<ENUM> enumClazz;

    public StringToEnumSerializer() {
    }

    public StringToEnumSerializer(Class<ENUM> enumClazz) {
        AssertionUtils.notNull(enumClazz, "null class not acceptable");
        this.enumClazz = enumClazz;
    }

    public Class<ENUM> getEnumClazz() {
        return enumClazz;
    }

    public void setEnumClazz(Class<ENUM> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @Override
    public ENUM convert(String value) {
        return Enum.valueOf(enumClazz, value);
    }
}

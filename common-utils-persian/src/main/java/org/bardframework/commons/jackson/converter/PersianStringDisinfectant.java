package org.bardframework.commons.jackson.converter;

import org.bardframework.commons.utils.persian.PersianStringUtils;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class PersianStringDisinfectant extends ConverterDeserializer<String, String> {

    @Override
    public String convert(String value) {
        return PersianStringUtils.disinfectPersianText(value);
    }
}

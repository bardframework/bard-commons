package org.bardframework.commons.jackson.converter;

import org.bardframework.commons.model.BaseData;
import org.bardframework.commons.util.StringUtils;
import org.bardframework.commons.util.UtilityMethods;
import org.springframework.stereotype.Component;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;


/**
 * Created by Vahid Zafari on 1/29/2016.
 */
@Component
public class StringToBaseDataConverter extends ConverterSerializer<String, BaseData> {

    @Override
    public BaseData convert(String value) {
        return StringUtils.hasText(value) ? UtilityMethods.toModel(value, value, 0, messageSource, getLocale()) : null;
    }
}

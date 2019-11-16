package org.bardframework.commons.jackson.converter;

import org.bardframework.commons.model.BaseData;
import org.bardframework.commons.util.UtilityMethods;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class BooleanToBaseDataConverter extends ConverterSerializer<Boolean, BaseData> {

    @Override
    public BaseData convert(Boolean value) {
        return UtilityMethods.toModel(value, messageSource, LocaleContextHolder.getLocale());
    }
}

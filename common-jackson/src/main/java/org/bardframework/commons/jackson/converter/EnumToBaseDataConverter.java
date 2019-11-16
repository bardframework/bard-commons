package org.bardframework.commons.jackson.converter;

import org.bardframework.commons.model.BaseData;
import org.bardframework.commons.util.UtilityMethods;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;


/**
 * Created by Vahid Zafari on 1/29/2016.
 */
public class EnumToBaseDataConverter extends ConverterSerializer<Enum, BaseData> {

    @Override
    public BaseData convert(Enum value) {
        return null == value ? null : UtilityMethods.toModel(value.name(), value.getClass().getSimpleName() + "." + value.name(), value.ordinal(), messageSource, getLocale());
    }
}

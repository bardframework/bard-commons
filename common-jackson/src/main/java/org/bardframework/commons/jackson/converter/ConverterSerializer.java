package org.bardframework.commons.jackson.converter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


/**
 * Created by Vahid Zafari on 1/29/2016.
 * for enable spring ioc, it is necessary to call super()
 */
public abstract class ConverterSerializer<INPUT, OUTPUT> extends ValueSerializer<INPUT> implements Converter<INPUT, OUTPUT> {

    @Autowired
    protected MessageSource messageSource;

    public ConverterSerializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public final void serialize(INPUT value, JsonGenerator jsonGenerator, SerializationContext serializerProvider) {
        if (null == value) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writePOJO(this.convert(value));
        }
    }
}

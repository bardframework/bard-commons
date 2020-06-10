package org.bardframework.commons.jackson.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

/**
 * Created by Vahid Zafari on 1/29/2016.
 * for enable spring ioc, it is necessary to call super()
 */
public abstract class ConverterSerializer<INPUT, OUTPUT> extends JsonSerializer<INPUT> implements Converter<INPUT, OUTPUT> {

    @Autowired
    protected MessageSource messageSource;

    public ConverterSerializer() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public final void serialize(INPUT value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (null == value) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeObject(this.convert(value));
        }
    }
}

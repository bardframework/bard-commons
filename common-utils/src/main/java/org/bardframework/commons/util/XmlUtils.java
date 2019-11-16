package org.bardframework.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class XmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

    private XmlUtils() {
    }

    public static <T> T xMLToObject(Class<T> containerClass, String url)
            throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(containerClass);
        context.createMarshaller().setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new FileReader(new File(url)));
    }

    public static void objectToXML(Object container, String url)
            throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(container.getClass()).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(container, new File(url));
    }
}

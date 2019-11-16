package org.bardframework.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class SoapUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapUtils.class);

    private SoapUtils() {
    }

    public static <T> T soapXMLResponseToObject(InputStream inputStream, Class<T> clazz)
            throws XMLStreamException, JAXBException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        StreamSource streamSource = new StreamSource(inputStream);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(streamSource);
        xmlStreamReader.nextTag(); // Advance to Envelope tag
        while (!xmlStreamReader.getLocalName().equals(clazz.getSimpleName())) {
            xmlStreamReader.nextTag();
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<T> jaxbElement = unmarshaller.unmarshal(xmlStreamReader, clazz);
        return jaxbElement.getValue();
    }
}

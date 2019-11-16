package org.bardframework.commons.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Base64Adapter extends XmlAdapter<String, byte[]> {

    @Override
    public byte[] unmarshal(String v) throws Exception {
        return v.getBytes();
    }

    @Override
    public String marshal(byte[] v) throws Exception {
        return new String(v);
    }
}

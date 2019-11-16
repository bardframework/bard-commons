package org.bardframework.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class SerializationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializationUtils.class);

    private SerializationUtils() {
    }

    public static byte[] serialize(Serializable object)
            throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        serialize(object, outputStream);
        return outputStream.toByteArray();
    }

    public static void serialize(Serializable object, OutputStream outputStream)
            throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(outputStream);
            out.writeObject(object);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public static <T> T deserialize(byte[] inBytes)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inBytes);
        return deserialize(inputStream);
    }

    public static <T> T deserialize(InputStream inputStream)
            throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(inputStream);
            return (T) in.readObject();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static byte[] serializeAndEncodeObject(Serializable obj) throws IOException {
        return serialize(obj);
    }

    public static <T> T deserializeAndCheckObject(byte[] object, Class<? extends Serializable> type)
            throws IOException, ClassNotFoundException {
        Object result = deserialize(object);
        if (!type.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Decoded object is of type " + result.getClass() + " when we were expecting " + type);
        } else {
            return (T) result;
        }
    }
}

package org.bardframework.commons.utils;

import lombok.experimental.UtilityClass;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Formatter;
import java.util.stream.IntStream;

@UtilityClass
public class EncodingUtils {

    public static String hexEncode(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        try (Formatter formatter = new Formatter(stringBuilder)) {
            IntStream.range(0, data.length).forEach(i -> formatter.format("%02x", data[i]));
            return stringBuilder.toString();
        }
    }

    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decodeBase64(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static byte[] decodeBase64(byte[] data) {
        return Base64.getDecoder().decode(data);
    }

    public static byte[] encodeBase64ToByteArray(byte[] data) {
        return Base64.getEncoder().encode(data);
    }

    public static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}

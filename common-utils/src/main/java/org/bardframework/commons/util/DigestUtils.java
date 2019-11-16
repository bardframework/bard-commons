package org.bardframework.commons.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DigestUtils {

    private static final String SHA512 = "SHA512";
    private static final String SHA = "SHA";

    private DigestUtils() {
    }

    public static String sha512(String data)
            throws NoSuchAlgorithmException {
        return digest(SHA512, data.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha(String data)
            throws NoSuchAlgorithmException {
        return digest(SHA, data);
    }

    public static byte[] sha(byte[] data)
            throws NoSuchAlgorithmException {
        return rawDigest(SHA, data);
    }

    public static String digest(String alg, String data)
            throws NoSuchAlgorithmException {
        return digest(alg, data.getBytes(StandardCharsets.UTF_8));
    }

    public static String digest(String alg, byte[] data)
            throws NoSuchAlgorithmException {
        return EncodingUtils.hexEncode(rawDigest(alg, data));
    }

    public static byte[] rawDigest(String alg, byte[] data)
            throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(alg).digest(data);
    }
}

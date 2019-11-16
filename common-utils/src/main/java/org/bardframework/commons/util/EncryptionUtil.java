package org.bardframework.commons.util;

import org.bardframework.commons.exception.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

/**
 * Created by Vahid Zafari on 4/21/2016.
 */
public final class EncryptionUtil {

    private static final String ALGORITHM = "AES";

    private EncryptionUtil() {
    }

    /**
     * @param rawData
     * @param key
     * @return if rawDate==null then returns  byte object with zero length
     * @throws CryptoException
     */
    public static byte[] encrypt(byte[] rawData, byte[] key)
            throws CryptoException {
        if (null == rawData) {
            return new byte[0];
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(null == key ? null : new String(key, StandardCharsets.UTF_8)));
            return Base64.getEncoder().encode(cipher.doFinal(rawData));
        } catch (Exception e) {
            throw new CryptoException("encryption exception", e);
        }
    }

    public static String encrypt(String rawData, String key)
            throws CryptoException {
        return new String(encrypt(null == rawData ? null : rawData.getBytes(StandardCharsets.UTF_8), null == key ? null : key.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * @param encryptedData
     * @param key
     * @return if encryptedData==null then returns  byte object with zero length
     * @throws CryptoException
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] key)
            throws CryptoException {
        if (null == encryptedData) {
            return new byte[0];
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(null == key ? null : new String(key, StandardCharsets.UTF_8)));
            return cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        } catch (Exception e) {
            throw new CryptoException("decryption exception", e);
        }
    }

    public static String decrypt(String encryptedData, String key)
            throws CryptoException {
        return new String(decrypt(null == encryptedData ? null : encryptedData.getBytes(StandardCharsets.UTF_8), null == key ? null : key.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    private static Key generateKey(String key) {
        if (null == key || key.trim().isEmpty()) {
            key = "Y~%7Sc#@Rt)(K&^Y";
        }
        int i = 0;
        StringBuilder builder = new StringBuilder(key);
        while (16 > builder.length()) {
            i++;
            builder.append(key.hashCode() + (byte) i + ++i);
        }
        return new SecretKeySpec(builder.substring(0, 16).getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }
}

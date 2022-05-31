package org.bardframework.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Demonstrate default Charset-related details.
 */

public final class CharsetUtils {

    private CharsetUtils() {
        /*
            prevent instantiation
         */
    }

    /**
     * Returns a Charset for the named charset. If the name is null, return the default Charset.
     *
     * @param charset The name of the requested charset, may be null.
     * @return a Charset for the named charset
     * @throws java.nio.charset.UnsupportedCharsetException If the named charset is unavailable
     */
    public static Charset toCharset(final String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }

    /**
     * Returns the given Charset or the default Charset if the given Charset is null.
     *
     * @param charset A charset or null.
     * @return the given Charset or the default Charset if the given Charset is null
     */
    public static Charset toCharset(final Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * Supplies the default encoding without using Charset.defaultCharset()
     * and without accessing System.getProperty("file.encoding").
     *
     * @return Default encoding (default charset).
     */

    public static String getDefaultEncoding() {
        final byte[] bytes = {'D'};
        final InputStream inputStream = new ByteArrayInputStream(bytes);
        final InputStreamReader reader = new InputStreamReader(inputStream);
        return reader.getEncoding();
    }

    public static Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    public static Charset getDefaultCharset() {
        return Charset.defaultCharset();
    }

    /**
     * @return sun.jnu.encoding
     */
    public static String getSunEncoding() {
        return System.getProperty("sun.jnu.encoding");
    }

    /**
     * @return file.encoding
     */
    public static String getFileEncoding() {
        return System.getProperty("file.encoding");
    }
}

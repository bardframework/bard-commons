package org.bardframework.commons.spring.boot;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Demonstrate default Charset-related details.
 */

class CharsetUtils {

    private CharsetUtils() {
    }

    /**
     * Supplies the default encoding without using Charset.defaultCharset()
     * <p>
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

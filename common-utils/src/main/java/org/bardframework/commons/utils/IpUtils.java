package org.bardframework.commons.utils;

/**
 * @author v.zafari@chmail.ir
 */
public final class IpUtils {

    private IpUtils() {
    }

    public static String formatIp(String ip) {
        StringBuilder builder = new StringBuilder();
        try {
            for (String num : ip.split("\\.")) {
                short part = Short.parseShort(num);
                if (part < 0) {
                    throw new IllegalArgumentException(ip);
                }
                builder.append(String.format("%03d", part));
                builder.append(StringUtils.DOT_CHAR);
            }
            String finalIp = builder.substring(0, builder.length() - 1);
            if (finalIp.length() != 15) {
                throw new IllegalArgumentException(ip);
            }
            return finalIp;
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException(String.format("error validating ip %s", ip), ex);
        }
    }
}

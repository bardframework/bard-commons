package org.bardframework.commons.utils;

import java.text.NumberFormat;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class MoneyUtils {

    private MoneyUtils() {
    }

    /**
     * @param money
     * @return get long number and return that in string format with money mask
     * @throws
     */
    public static String moneyMask(long money) {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        return numberFormatter.format(money);
    }

    public static String moneyMask(double money) {
        NumberFormat numberFormater = NumberFormat.getNumberInstance();
        return numberFormater.format(money);
    }
}

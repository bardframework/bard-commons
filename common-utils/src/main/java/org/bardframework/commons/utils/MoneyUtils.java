package org.bardframework.commons.utils;

import lombok.experimental.UtilityClass;

import java.text.NumberFormat;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
@UtilityClass
public class MoneyUtils {

    /**
     * @return get long number and return that in string format with money mask
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

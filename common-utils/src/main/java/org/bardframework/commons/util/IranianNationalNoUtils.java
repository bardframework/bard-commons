package org.bardframework.commons.util;

import java.util.Set;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class IranianNationalNoUtils {

    private static final Set<Long> nationalNoBlackList = CollectionUtils.toSet(Long.valueOf("0123456789"),
            Long.valueOf("1111111111"), Long.valueOf("2222222222"), Long.valueOf("3333333333"),
            Long.valueOf("4444444444"), Long.valueOf("5555555555"), Long.valueOf("6666666666"),
            Long.valueOf("7777777777"), Long.valueOf("8888888888"), Long.valueOf("9999999999"));

    private IranianNationalNoUtils() {
    }

    /**
     * @param nationalNo
     * @return if given nationalNo is a valid number it return true else return
     * false
     * @throws
     */
    public static boolean checkNationalNo(long nationalNo) {
        if (nationalNo < 10000000 || nationalNoBlackList.contains(nationalNo)) {
            return false;
        }
        String temp = String.format("%010d", nationalNo);
        int sum = 0;
        int i;
        for (i = 0; i < 9; i++) {
            sum += Integer.parseInt(temp.substring(i, i + 1)) * (10 - i);
        }
        sum = sum % 11;
        return Integer.parseInt(temp.substring(i, i + 1)) == (sum < 2 ? sum : 11 - sum);
    }
}

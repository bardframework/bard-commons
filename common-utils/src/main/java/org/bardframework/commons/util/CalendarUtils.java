package org.bardframework.commons.util;

import java.util.Calendar;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class CalendarUtils {

    private CalendarUtils() {
    }

    public static String getString(Calendar calendar) {
        return String.format("%04d", calendar.get(Calendar.YEAR)) + "/"
                + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "/"
                + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + " "
                + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format("%02d", calendar.get(Calendar.MINUTE)) + ":"
                + String.format("%02d", calendar.get(Calendar.SECOND)) + ":"
                + String.format("%03d", calendar.get(Calendar.MILLISECOND));
    }
}

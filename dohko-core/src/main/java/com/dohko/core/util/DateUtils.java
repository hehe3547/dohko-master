package com.dohko.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiangbin on 2016/7/11.
 */
public class DateUtils {

    private static String PATTERN_DATE = "yyyyMMdd";
    private static String PATTERN_DATETIME = "yyyyMMddHHmmss";

    public static String getCurrentDate() {
        return new SimpleDateFormat(PATTERN_DATE).format(new Date());
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat(PATTERN_DATETIME).format(new Date());
    }
}

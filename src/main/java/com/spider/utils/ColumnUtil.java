package com.spider.utils;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: zhouwang
 * Date: 12-9-19
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class ColumnUtil {
    public static int getInt(Object value, int defaultVal) {
        if (value == null || value.equals("")) {
            return defaultVal;
        }
        return Integer.parseInt(value.toString());
    }

    public static long getLong(Object value, long defaultVal) {
        if (value == null || value.equals("")) {
            return defaultVal;
        }
        return Long.parseLong(value.toString());
    }

    public static String getSting(Object value, String defaultVal) {
        if (value == null || value.equals("")) {
            return defaultVal;
        }
        return value.toString().trim();
    }

    public static long getDateToLong(Object value, long defaultVal) {
        if (value == null || value.equals("")) {
            return defaultVal;
        }
        return Timestamp.valueOf(value.toString()).getTime();
    }


    public static String formatContent(String value){
        return value.replaceAll("'","\'");
    }

}

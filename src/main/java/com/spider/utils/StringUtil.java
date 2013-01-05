package com.spider.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-7-12
 * Time: 上午9:40
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    /**
     * 格式化String为Integer，如果失败，取默认值
     */
    public static int formatInt(String num, int defaultValue) {
        try {
            return Integer.parseInt(num);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public static long formatLong(String num, long defaultValue) {
        try {
            return Long.parseLong(num);
        } catch (Throwable e) {
            return defaultValue;
        }
    }
    public static String ensureNotEmptyAndNotNull(String str, String defaultValue) {
        if (str == null || str.trim().length() <= 0) {
            return defaultValue;
        }
        return str;
    }
    public static String ensureNotEmpty(String str, String defaultValue) {
        if (str == null || str.trim().length() <= 0) {
            return defaultValue;
        }
        return str;
    }

    public static boolean isNull(String value) {
        if (value == null) {
            return true;
        }
        return false;
    }

    public static boolean notNull(String value) {
        return !isNull(value);
    }

    public static boolean notNullAndNotEmpty(String value) {
        if (notNull(value) && !value.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 限制字符串长度
     */

    public static String limitLen(String v, int limit) {
        if (v == null) {
            return "";
        }
        if (v.length() > limit) {
            return v.substring(0, limit) + "...";
        }
        return v;
    }


    public static String delNull(String value){
        return value.replaceAll("\\s","");
    }


    public static String showSQL(String sql, Object[] objs) {
        for (Object obj : objs) {
            sql = sql.replaceFirst("\\?", "'" + obj.toString() + "'");
        }
        System.out.println(sql);
        return sql;
    }

}

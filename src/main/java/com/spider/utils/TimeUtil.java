package com.spider.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhouwang
 * Date: 12-4-23
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MIN_FORMAT = "yyyy-MM-dd HH:mm";


    /**
     * @param timestamp
     * @param format    (所需转换成的格式)
     * @return String 转换后的时间字符串
     * @author wangzhou
     */
    public static String timestampToStrByFormat(Timestamp timestamp, String format) {
        return new SimpleDateFormat(format).format(new Date(timestamp.getTime()));
    }

    public static String dateToStrByFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Timestamp dateToTimestamp(final Date date) {
        return new Timestamp(date.getTime());
    }

    public static long timeStrToLongByFormat(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    public static Timestamp nextDay(String time) {

        Timestamp tm = Timestamp.valueOf(time);
        tm = new Timestamp(tm.getTime() + 24 * 60 * 60 * 1000);
        return tm;
    }

    public static String curTime(){
        return new SimpleDateFormat(DEFAULT_FORMAT).format(System.currentTimeMillis());
    }

    public static long timeOf(String time) throws ParseException {
        return new SimpleDateFormat(DEFAULT_FORMAT).parse(time).getTime();
    }

}

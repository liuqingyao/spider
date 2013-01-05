package com.spider.utils;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-9-7
 * Time: 下午6:17
 * To change this template use File | Settings | File Templates.
 */
public class IDUtil {
    public static String getImageId() {
        return UUID.randomUUID().toString();
    }
}

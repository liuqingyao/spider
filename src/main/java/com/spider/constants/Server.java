package com.spider.constants;

import com.spider.utils.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-27
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class Server {
    private static Map<String, String> map = new HashMap<String, String>();
    private static final String CONFIG = "server.properties";
    private static boolean init = false;

    public Server() {
        init();
    }

    public static String getValue(String key) {
        if (!init) init();
        return map.get(key);
    }

    public static void init() {
        if (init) {
            return;
        }
        map = PropertiesUtil.getSimpleMap(CONFIG);
        init = true;
    }

    public static String getValue(String key, String defaultValue) {
        String v = getValue(key);
        if (v == null) {
            return defaultValue;
        }
        return v;
    }
}

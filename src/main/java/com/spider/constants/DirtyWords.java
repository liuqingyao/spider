package com.spider.constants;

import com.spider.utils.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-26
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class DirtyWords {
    private static Map<String, String> map = new HashMap<String, String>();
    private static final String CONFIG = "dirty.properties";
    private static boolean init = false;

    public DirtyWords(){
        init();
    }

    public static String getValue(String key) {
        if(!init) init();
        return map.get(key);
    }
    public static void init() {
        if(init){
            return;
        }
        map = PropertiesUtil.getSimpleMap(CONFIG);
        init = true;
    }
}

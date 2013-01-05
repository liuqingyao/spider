package com.spider.constants;

import com.spider.utils.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-1-29
 * Time: 下午6:51
 * 获取环境变量值
 */
public class Path {

    public static String getPath(String name){
        return System.getenv(name);
    }

}

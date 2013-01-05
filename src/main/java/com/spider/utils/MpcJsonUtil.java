package com.spider.utils;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-4-1
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class MpcJsonUtil {

    public static String errorJsonResponse(String msg){
        Map error = new HashMap();
        error.put("status",-1);
        error.put("msg",msg);
        error.put("data","");
        return new JSONObject(error).toString();
    }

    public static String successJson(Map data){
        Map success = new HashMap();
        success.put("status",0);
        success.put("msg","OK");
        success.put("data",data);
        return new JSONObject(success).toString();
    }

}

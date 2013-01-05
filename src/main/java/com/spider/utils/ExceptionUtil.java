package com.spider.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-5
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionUtil {
    public static String stackInfo(Exception exception){
        StringWriter writer = new StringWriter();
        try{
            writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            return  writer.getBuffer().toString();
        }finally {
            if(writer != null)
                try {
                    writer.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}

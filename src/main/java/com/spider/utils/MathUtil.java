package com.spider.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-10
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
public class MathUtil {
   public static boolean between(long min,long max,long v){
      return  v>min && v<max;
   }
}

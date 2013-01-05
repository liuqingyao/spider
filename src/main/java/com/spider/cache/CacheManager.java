package com.spider.cache;

import com.spider.cache.memcached.RedisCacheImpl;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-18
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class CacheManager {
     private static Cache cache = RedisCacheImpl.getInstance();
     public static Cache getCacheImpl(){
         return cache;
     }
}

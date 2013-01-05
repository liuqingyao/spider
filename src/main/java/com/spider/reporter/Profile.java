package com.spider.reporter;

import com.spider.cache.Cache;
import com.spider.cache.CacheManager;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-7
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class Profile {
    private static Cache cache = CacheManager.getCacheImpl();

    public static final String  DEFAULT_PREFIX = "spider_monitor_";
    private String key = "";
    private long startTime = 0;
    private long endTime = 0;

    private Profile(String key){
        this.key = key;
        start();
    }

    public static Profile getProfileOf(String key){
        return  new Profile(key);
    }

    public void start(){
         this.startTime = System.currentTimeMillis();
    }
    public void end(){
        this.endTime = System.currentTimeMillis();
        cache.rpush(DEFAULT_PREFIX+key,(endTime - startTime)+"");
    }

}

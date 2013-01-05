package com.spider.job.queue;

import com.spider.bean.SourceVO;
import com.spider.cache.Cache;
import com.spider.cache.CacheManager;
import com.spider.reporter.Reporter;
import com.spider.service.util.SourceUtil;
import com.spider.utils.StringUtil;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-10-17
 * Time: 下午12:35
 * To change this template use File | Settings | File Templates.
 */
public class SourceQueue {
    private static SourceQueue queue = new SourceQueue();

    private SourceQueue() {
    }

    public static SourceQueue getInstance() {
        return queue;
    }

    private static Cache cache = CacheManager.getCacheImpl();
    public static final String KEY = "source_in_spider_5_new";
    public static final String KEY_LESS_5 = "source_in_spider_less_5";

    Random random = new Random();

    private static AtomicInteger l5Count = new AtomicInteger(0);  //10:1的抓取比例
    private static final int BALANCE = 10;

    public long size() {
        return sizeOfImportantSource() + sizeOfUnimportantSource();
    }

    public long sizeOfImportantSource() {

        return cache.llen(KEY);
    }

    public long sizeOfUnimportantSource() {
        return cache.llen(KEY_LESS_5);
    }

    public SourceVO take() {
//        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        if(hour > 23 || hour < 4){
//            return strategyB();
//        }
        return strategyA();
    }

    public SourceVO strategyA() {
        SourceVO sourceVO = takeImportant();
        if (sourceVO == null) {
            sourceVO = takeUnimportant();
        }
        return sourceVO;
    }

    public SourceVO strategyB() {
        int r = random.nextInt(2);
        if (r == 1) {
            return takeImportant();
        }
        return takeUnimportant();
    }

    public SourceVO takeImportant() {
        if (l5Count.incrementAndGet() >= BALANCE) {
            return null;
        }
        String sourceValue = cache.lpop(KEY);
        if (StringUtil.notNullAndNotEmpty(sourceValue)) {
            return SourceUtil.stringToSource(sourceValue);
        }
        return null;
    }

    public SourceVO takeUnimportant() {
        l5Count.set(0);
        String sourceValue = cache.lpop(KEY_LESS_5);
        if (StringUtil.notNullAndNotEmpty(sourceValue)) {
            return SourceUtil.stringToSource(sourceValue);
        }
        return null;
    }

    public boolean offerImportantSource(SourceVO value) {
        Reporter.debug(Reporter.DEBUG_SOURCE_QUEUE, value.getSourceUrl());
        return cache.rpush(KEY, SourceUtil.sourceToString(value));
    }

    public boolean offerUnimportantSource(SourceVO value) {
        Reporter.debug(Reporter.DEBUG_SOURCE_QUEUE, value.getSourceUrl());
        return cache.rpush(KEY_LESS_5, SourceUtil.sourceToString(value));
    }

}

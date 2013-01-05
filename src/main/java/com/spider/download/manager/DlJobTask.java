package com.spider.download.manager;

import com.spider.cache.Cache;
import com.spider.cache.CacheManager;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: zhouwang
 * Date: 12-9-20
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */

public class DlJobTask implements Callable {
    private static Cache cache = CacheManager.getCacheImpl();

    @Override
    public Object call() throws Exception {

        //TODO 队列中取出下载的URL
        //TODO 判断URL的类型，文本还是图片
        //TODO 图片地址处理
        //TODO 文本URL处理

        //TODO 通知图片和文本地址处理完毕

        return null;
    }

}

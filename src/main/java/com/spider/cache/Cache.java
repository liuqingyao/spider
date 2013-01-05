package com.spider.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-16
 * Time: 下午6:28
 * To change this template use File | Settings | File Templates.
 */
public interface Cache {
    void set(String key, String value, int seconds);

    String get(String key);

    void del(String... keys);

    long sadd(String key, String value);

    long zadd(String key,double score,String value);

    long zadd(String key,Map<Double,String> scoredMembers);

    Set<String> zrange(String key,long start,long end);

    String spop(String key);

    String zpop(String key);

    long srem(String key, String value);

    boolean sismember(String key, String value);

    Set<String> smembers(String key);

    List<String> mGet(String... keys);

    List<String> hvals(String key);

    long llen(String key);

    String lpop(String key);

    void lpush(String key, String value);

    boolean rpush(String key, String value);

    List<String> lrange(String key, long start, long end);

    boolean exists(String key);
}

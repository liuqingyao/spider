package com.spider.cache.memcached;

import com.spider.cache.Cache;
import com.spider.constants.Server;
import com.spider.job.queue.SourceQueue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-16
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public class RedisCacheImpl implements Cache {
    private static RedisCacheImpl redisCache = new RedisCacheImpl();

    private RedisCacheImpl() {
    }

    public static RedisCacheImpl getInstance() {
        return redisCache;
    }

    private static JedisPool pool;

    static {
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxActive(200);
        jedisConfig.setMinIdle(2);
        jedisConfig.setMaxWait(5000);
        pool = new JedisPool(jedisConfig, Server.getValue("redis_ip","10.11.13.68"), Integer.valueOf(Server.getValue("redis_port","12255")));
    }


    @Override
    public void set(String key, String value, int seconds) {
        if (key == null || value == null) {
            return;
        }
        Jedis jedis = pool.getResource();
        try{
            jedis.setex(key, seconds, value);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public String get(String key) {
        if (key == null) {
            return null;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.get(key);
        }finally {
            pool.returnResource(jedis);
        }
    }

    public List<String> mGet(String... keys) {
        for (String s : keys) {
            if (s == null) {
                return null;
            }
        }
        Jedis jedis = pool.getResource();
        try{
           return jedis.mget(keys);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public List<String> hvals(String key) {
        if (key == null) {
            return null;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.hvals(key);
        }finally {
            pool.returnResource(jedis);
        }
    }

    public boolean rpush(String key, String value) {
        if (key == null || value == null) {
            return false;
        }
        Jedis jedis = pool.getResource();
        try{
            return   jedis.rpush(key, value) > 0;
        }finally {
            pool.returnResource(jedis);
        }
    }

    public long llen(String key) {
        if (key == null) {
            return 0;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.llen(key);
        }finally {
            pool.returnResource(jedis);
        }

    }

    public String lpop(String key){
        if (key == null) {
            return null;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.lpop(key);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void lpush(String key, String value) {
        if (key == null || value == null) {
            return ;
        }
        Jedis jedis = pool.getResource();
        try{
            jedis.lpush(key, value);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        if (key == null) {
            return null;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.lrange(key, start, end);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public boolean exists(String key) {
        if (key == null) {
            return false;
        }
        Jedis jedis = pool.getResource();
        try{
           return jedis.exists(key);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void del(String... keys) {
        if (keys == null) {
            return;
        }
        Jedis jedis = pool.getResource();
        try{
            jedis.del(keys);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public long sadd(String key, String value) {
        if (key == null || value == null) {
            return 0;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.sadd(key, value);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public long srem(String key, String value) {
        if (key == null || value == null) {
            return 0;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.srem(key, value);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public boolean sismember(String key, String value) {
        if (key == null || value == null) {
            return false;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.sismember(key, value);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public Set<String> smembers(String key) {
        if (key == null) {
            return null;
        }
        Jedis jedis = pool.getResource();
        try{
            return jedis.smembers(key);
        }finally {
            pool.returnResource(jedis);
        }
    }

    public long zadd(String key,double score,String value){
        if(key==null||value==null)return 0;
        Jedis jedis = pool.getResource();
        try{
            return jedis.zadd(key,score,value);
        }finally {
            pool.returnResource(jedis);
        }

    }

    public long zadd(String key,Map<Double,String> scoredMembers){
        if(key==null||scoredMembers==null||scoredMembers.size()<1)return 0;
        Jedis jedis = pool.getResource();
        try{
            return jedis.zadd(key,scoredMembers);
        }finally {
            pool.returnResource(jedis);
        }

    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        if(key==null)return null;
        Jedis jedis = pool.getResource();
            try{
            return jedis.zrange(key, start, end);
        }finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public String spop(String key) {
        if(key==null)return null;
        Jedis jedis = pool.getResource();
        try{

            return jedis.spop(key);
        }finally {
            pool.returnResource(jedis);
        }
    }

    public String zpop(String key){
        if(key==null)return null;
        Jedis jedis = pool.getResource();
        String result = "";
        try{
            Set<String> members = jedis.zrange(key,0,0);
            if(members.size()<1){
                 return null;
            }
            for(String mem:members){
               result = mem;
                break;
            }
            jedis.zrem(key,result);
            return result;
        }finally {
            pool.returnResource(jedis);
        }
    }

}


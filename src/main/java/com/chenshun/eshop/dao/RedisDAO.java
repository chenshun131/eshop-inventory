package com.chenshun.eshop.dao;

import com.chenshun.eshop.util.SerializationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * User: mew <p />
 * Time: 18/5/7 09:41  <p />
 * Version: V1.0  <p />
 * Description: Redis 操作类 <p />
 */
@CacheConfig(cacheNames = "redisCache")
@Repository
public class RedisDAO {

    @Resource
    private JedisCluster jedisCluster;

    public String set(String key, String value) {
        // 设置缓存时间一小时
        int timeout = 60 * 60;
        return jedisCluster.setex(SerializationUtil.serialize(key), timeout, SerializationUtil.serialize(value));
    }

    public String get(String key) {
        byte[] result = jedisCluster.get(SerializationUtil.serialize(key));
        return SerializationUtil.deserialize(result, String.class);
    }

}

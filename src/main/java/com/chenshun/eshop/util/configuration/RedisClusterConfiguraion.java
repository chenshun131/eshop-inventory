package com.chenshun.eshop.util.configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * User: mew <p />
 * Time: 18/5/9 16:17  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class RedisClusterConfiguraion {

    /** 数据库链接池配置 */
    private JedisPoolConfig config = new JedisPoolConfig();

    /** Redis集群的节点集合 */
    private Set<HostAndPort> jedisClusterNodes = new HashSet<>();

    private int connectionTimeout;

    private int soTimeout;

    private int maxAttempts;

    public JedisPoolConfig getConfig() {
        return config;
    }

    public void setConfig(JedisPoolConfig config) {
        this.config = config;
    }

    public Set<HostAndPort> getJedisClusterNodes() {
        return jedisClusterNodes;
    }

    public void setJedisClusterNodes(Set<HostAndPort> jedisClusterNodes) {
        this.jedisClusterNodes = jedisClusterNodes;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

}

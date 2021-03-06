package com.chenshun.eshop.util.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: mew <p />
 * Time: 18/5/7 17:16  <p />
 * Version: V1.0  <p />
 * Description: 请求内存队列 <p />
 */
public class RequestQueue {

    /** 内存队列 */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    /** 标识位map */
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    /**
     * 采用绝对线程安全的方式实现单利
     */
    private static class Singleton {

        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }

    }

    /**
     * 采用 JVM 的机制去保证多线程并发安全 <br/>
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     *
     * @return
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 添加一个内存队列
     *
     * @param queue
     */
    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    /**
     * 获取内存队列的数量
     *
     * @return
     */
    public int queueSize() {
        return queues.size();
    }

    /**
     * 获取内存队列
     *
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }

}

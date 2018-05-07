package com.chenshun.eshop.util.thread;

import com.chenshun.eshop.util.request.Request;
import com.chenshun.eshop.util.request.RequestQueue;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.*;

/**
 * User: mew <p />
 * Time: 18/5/7 17:26  <p />
 * Version: V1.0  <p />
 * Description: 请求处理线程池，单例 <p />
 */
public class RequestProcessorThreadPool {

    @Value("${common.threadpool.threadcount}")
    private int threadcount;

    private ExecutorService threadPool;

    {
        threadPool = new ThreadPoolExecutor(threadcount, threadcount, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < threadcount; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
            requestQueue.add(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    /**
     * 采用静态内部类的方式实现单例
     */
    private static class Singleton {

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }

    }

    /**
     * 采用 JVM 的机制去保证多线程并发安全 <br/>
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     *
     * @return
     */
    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 初始化
     */
    public static void init() {
        getInstance();
    }

}

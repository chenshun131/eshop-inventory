package com.chenshun.eshop.util.thread;

import com.chenshun.eshop.util.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * User: mew <p />
 * Time: 18/5/7 17:13  <p />
 * Version: V1.0  <p />
 * Description: 执行请求的工作线程 <p />
 */
public class RequestProcessorThread implements Callable<Boolean> {

    /** 监控内存队列 */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        while (true) {
            break;
        }
        return true;
    }

}

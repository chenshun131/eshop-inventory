package com.chenshun.eshop.service;

import com.chenshun.eshop.util.request.Request;

/**
 * User: mew <p />
 * Time: 18/5/8 14:01  <p />
 * Version: V1.0  <p />
 * Description: 请求异步执行 <p />
 */
public interface RequestAsyncProcessService {

    /**
     * 将 Request 放入相应的请求队列中，通过线程池中的线程执行 Request 的 process() 方法
     *
     * @param request
     */
    void process(Request request);

}

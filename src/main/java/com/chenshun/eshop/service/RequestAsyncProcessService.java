package com.chenshun.eshop.service;

import com.chenshun.eshop.util.request.Request;

/**
 * User: mew <p />
 * Time: 18/5/8 14:01  <p />
 * Version: V1.0  <p />
 * Description: 请求异步执行 <p />
 */
public interface RequestAsyncProcessService {

    void process(Request request);

}

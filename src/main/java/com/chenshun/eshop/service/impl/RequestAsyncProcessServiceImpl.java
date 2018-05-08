package com.chenshun.eshop.service.impl;

import com.chenshun.eshop.service.RequestAsyncProcessService;
import com.chenshun.eshop.util.request.Request;
import com.chenshun.eshop.util.request.RequestQueue;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * User: mew <p />
 * Time: 18/5/8 14:05  <p />
 * Version: V1.0  <p />
 * Description: 请求异步处理 <p />
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    @Override
    public void process(Request request) {
        try {
            // 做请求的路由，根据每个请求的商品id，路由到对应的内存队列中
            ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
            // 将请求放入对应的队列中，完成路由操作
            queue.put(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取路由到内存的列表
     *
     * @param productId
     *         商品id
     * @return 内存列表
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        // 先获取 productId 的 hash 值
        String key = (productId == null) ? null : productId.toString();
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        // 所以任何一个商品id都会被固定到同样一个内存队列中去
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }

}

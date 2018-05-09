package com.chenshun.eshop.service.impl;

import com.chenshun.eshop.service.RequestAsyncProcessService;
import com.chenshun.eshop.util.request.ProductInventoryCacheRefreshRequest;
import com.chenshun.eshop.util.request.ProductInventoryDBUpdateRequest;
import com.chenshun.eshop.util.request.Request;
import com.chenshun.eshop.util.request.RequestQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * User: mew <p />
 * Time: 18/5/8 14:05  <p />
 * Version: V1.0  <p />
 * Description: 请求异步处理 <p />
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Request request) {
        try {
            // 先做读请求的去重
            RequestQueue requestQueue = RequestQueue.getInstance();
            Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
            if (request instanceof ProductInventoryDBUpdateRequest) {
                // 如果是一个更新数据库的请求，那么久将那个 productId 对应的表示设置为 true
                flagMap.put(request.getProductId(), true);
            } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                Boolean flag = flagMap.get(request.getProductId());
                if (flag == null) {
                    // 如果 flag 是 null
                    flagMap.put(request.getProductId(), false);
                } else {
                    if (flag) {
                        // 如果是缓存刷新的请求，那么就判断，如果标识不为空，而且是 true，就说明之前有一个这个商品的数据库更新请求
                        flagMap.put(request.getProductId(), false);
                    } else {
                        // 如果是缓存刷新的请求，而且发现标识不为空，但是标识是false
                        // 说明前面已经有 一个数据库更新请求+一个缓存刷新请求
                        // 对于这种读请求，直接救过滤掉，不要放到后面的内存队列里面去
                        return;
                    }
                }
            }
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
        logger.debug("路由内存队列，商品id={}, 队列索引={}", productId, index);
        return requestQueue.getQueue(index);
    }

}

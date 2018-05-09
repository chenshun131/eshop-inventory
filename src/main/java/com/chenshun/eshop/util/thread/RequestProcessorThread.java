package com.chenshun.eshop.util.thread;

import com.chenshun.eshop.util.request.ProductInventoryCacheRefreshRequest;
import com.chenshun.eshop.util.request.ProductInventoryDBUpdateRequest;
import com.chenshun.eshop.util.request.Request;
import com.chenshun.eshop.util.request.RequestQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * User: mew <p />
 * Time: 18/5/7 17:13  <p />
 * Version: V1.0  <p />
 * Description: 执行请求的工作线程 <p />
 */
public class RequestProcessorThread implements Callable<Boolean> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 监控内存队列 */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                // ArrayBlockingQueue
                // Blocking就是说明，如果队列满，或者是空的，那么都会在执行操作的时候，阻塞住
                Request request = queue.take();
                boolean forceRefresh = request.isForceRefresh();

                // 先做读请求的去重
                if (!forceRefresh) {
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
                    if (request instanceof ProductInventoryDBUpdateRequest) {
                        // 如果是一个更新数据库的请求，那么就将那个 productId 对应的标识设置为 true
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                        Boolean flag = flagMap.get(request.getProductId());
                        if (flag == null) {
                            // 如果 flag 是null，说明在这之前都没有对数据进行更新操作(数据存不存在未知)，也没有获取数据到缓存，此时进行 从数据读取数据+更新缓存
                            flagMap.put(request.getProductId(), false);
                        } else {
                            if (flag) {
                                // 如果是缓存刷新的请求，那么就判断，如果标识不为空，而且是true，就说明之前有一个这个商品的数据库更新请求
                                flagMap.put(request.getProductId(), false);
                            } else {
                                // 如果是缓存刷新的请求，那么就判断，如果标识不为空，但是标识是false
                                // 说明前面已经有 一个数据库更新请求+一个缓存刷新请求
                                return true;
                            }
                        }
                    }
                }
                logger.debug("工作线程处理请求，商品id={}", request.getProductId());
                // 执行这个 request 操作
                request.process();
                // 假如说，执行完了一个读请求之后，假设数据已经刷新到 Redis 中了
                // 但是后面可能 Redis 中的数据会因为内存满了，被自动清理掉
                // 如果说数据从 Redis 中被自动清理掉了以后
                // 然后后面又来一个读请求，此时如果进来，发现标志位是 false，就不会去执行这个刷新的操作了
                // 所以在执行完这个读请求之后，实际上这个标志位是停留在 false的
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}

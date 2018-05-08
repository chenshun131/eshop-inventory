package com.chenshun.eshop.controller;

import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;
import com.chenshun.eshop.service.RequestAsyncProcessService;
import com.chenshun.eshop.util.net.Response;
import com.chenshun.eshop.util.request.ProductInventoryCacheRefreshRequest;
import com.chenshun.eshop.util.request.ProductInventoryDBUpdateRequest;
import com.chenshun.eshop.util.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: mew <p />
 * Time: 18/5/8 17:32  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@RestController
public class ProductInventoryController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestAsyncProcessService requestAsyncProcessService;

    @Autowired
    private ProductInventoryService productInventoryService;

    /**
     * 更新商品库存
     *
     * @param productInventory
     * @return
     */
    @RequestMapping("updateProductInventory")
    public Response updateProductInventory(ProductInventory productInventory) {
        logger.debug("===========日志===========: 接收到更新商品库存的请求，商品id={}, 商品库存数量={}"
                , productInventory.getProductId(), productInventory.getInventoryCnt());
        Response response = null;
        try {
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    /**
     * 获取商品库存
     *
     * @param productId
     * @return
     */
    @RequestMapping("getProductInventory")
    public ProductInventory getProductInventory(Integer productId) {
        logger.debug("===========日志===========: 接收到一个商品库存的读请求，商品id={}", productId);
        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, false);
            requestAsyncProcessService.process(request);

            // 将请求扔给service异步去处理以后，就需要 while(true) 一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;

            // 等到超过200ms没有从缓存中获取到结果
            while (true) {
//                if (waitTime > 2500) {
//                    break;
//                }

                if (waitTime > 200) {
                    break;
                }
                // 尝试去从redis中读取一次商品库存的缓存数据
                productInventory = productInventoryService.getProductInventoryCache(productId);
                // 如果读取到结果，那么就返回
                if (productInventory != null) {
                    logger.debug("===========日志===========: 在200ms内读取到了redis中的库存缓存，商品id={}, 商品库存数量={}",
                            productInventory.getProductId(), productInventory.getInventoryCnt());
                    return productInventory;
                } else {
                    // 如果没有读取到结果，那么等待一段时间
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            // 直接尝试从数据库中读取数据
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                // 将缓存刷新一下
                // 这个过程，实际上是一个读操作过程，但是没有放在队列中串行去处理，还是有数据不一致问题
                request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService, true);
                requestAsyncProcessService.process(request);

                // 代码会运行到这里，只有三种情况：
                // 1、就是说，上一次也是读请求，数据刷入了redis，但是redis LRU算法给清理掉了，标志位还是false
                // 所以此时下一个读请求是从缓存中拿不到数据的，再放一个读Request进队列，让数据去刷新一下
                // 2、可能在200ms内，就是读请求在队列中一直积压着，没有等待到它执行（在实际生产环境中，基本是比较坑了）
                // 所以就直接查一次库，然后给队列里塞进去一个刷新缓存的请求
                // 3、数据库里本身就没有，缓存穿透，穿透redis，请求到达mysql库

                return productInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }

}

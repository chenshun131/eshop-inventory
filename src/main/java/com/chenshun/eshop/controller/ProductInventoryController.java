package com.chenshun.eshop.controller;

import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;
import com.chenshun.eshop.service.RequestAsyncProcessService;
import com.chenshun.eshop.util.net.Response;
import com.chenshun.eshop.util.request.ProductInventoryCacheRefreshRequest;
import com.chenshun.eshop.util.request.ProductInventoryDBUpdateRequest;
import com.chenshun.eshop.util.request.Request;
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
        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);
            requestAsyncProcessService.process(request);

            // 将请求扔给service异步去处理以后，就需要 while(true) 一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;

            // 等到超过200ms没有从缓存中获取到结果
            while (true) {
                if (waitTime > 200) {
                    break;
                }
                // 尝试去从
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }

}

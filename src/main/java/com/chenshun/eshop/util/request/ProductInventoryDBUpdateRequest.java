package com.chenshun.eshop.util.request;

import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: chenshun131 <p />
 * Time: 18/5/7 23:01  <p />
 * Version: V1.0  <p />
 * Description:
 * 比如说一个商品发生了交易，那么就要修改这个商品对应的库存 <br/>
 * 此时就会发送请求过来，要求修改库存，那么这个可能就是所谓的data update request，数据更新请求  <br/>
 * <p>
 * cache aside pattern <br/>
 * （1）删除缓存  <br/>
 * （2）更新数据库 <p />
 */
public class ProductInventoryDBUpdateRequest implements Request {

    private Logger logger = LoggerFactory.getLogger(ProductInventoryDBUpdateRequest.class);

    /** 产品库存 */
    private ProductInventory productInventory;

    /** 商品库存 Service */
    private ProductInventoryService productInventoryService;

    /**
     * @param productInventory
     *         产品库存
     * @param productInventoryService
     *         商品库存 Service
     */
    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        logger.debug("数据库更新请求开始执行，商品id={}, 商品库存数量={}", productInventory.getProductId(), productInventory.getInventoryCnt());
        // 删除 Redis 中的缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        // 为模拟演示先删除 redis 中的缓存，然后还没更新数据库的时候，读请求过来了，这里可以 sleep 一下
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 修改数据库中的库存
        productInventoryService.updateProductInventory(productInventory);
    }

    /**
     * 获取产品 id
     *
     * @return
     */
    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }

}

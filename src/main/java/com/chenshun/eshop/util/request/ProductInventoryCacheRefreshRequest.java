package com.chenshun.eshop.util.request;

import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mew <p />
 * Time: 18/5/8 09:27  <p />
 * Version: V1.0  <p />
 * Description: 重新加载商品库存的缓存 <p />
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 商品id */
    private Integer productId;

    /** 商品库存 Service */
    private ProductInventoryService productInventoryService;

    /** 是否强制刷新缓存 */
    private boolean forceRefresh;

    /**
     * @param productId
     *         商品id
     * @param productInventoryService
     *         商品库存 Service
     * @param forceRefresh
     *         是否强制刷新缓存
     */
    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService, boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        logger.debug("已查询到商品最新的库存数量，商品id={}, 商品库存数量={}", productId, productInventory.getInventoryCnt());
        // 将最新的商品库存数量，刷新到 Redis 缓存中去
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }

}

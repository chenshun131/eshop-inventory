package com.chenshun.eshop.util.request;

import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;

/**
 * User: mew <p />
 * Time: 18/5/8 09:27  <p />
 * Version: V1.0  <p />
 * Description: 重新加载商品库存的缓存 <p />
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    /** 商品id */
    private Integer productId;

    /** 商品库存 Service */
    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        // 将最新的商品库存数量，刷新到 Redis 缓存中去
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

}

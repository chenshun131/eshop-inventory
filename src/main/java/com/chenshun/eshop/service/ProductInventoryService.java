package com.chenshun.eshop.service;

import com.chenshun.eshop.model.ProductInventory;

/**
 * User: chenshun131 <p />
 * Time: 18/5/7 23:03  <p />
 * Version: V1.0  <p />
 * Description: 产品库存接口 <p />
 */
public interface ProductInventoryService {

    /**
     * 更新产品库存
     *
     * @param productInventory
     *         产品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除 Redis 中的商品库存的缓存
     *
     * @param productInventory
     *         商品库存
     */
    void removeProductInventoryCache(ProductInventory productInventory);

    /**
     * 根据商品 id 查询商品库存
     *
     * @param productId
     *         商品id
     * @return 商品库存
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 设置商品库存的缓存
     *
     * @param productInventory
     */
    void setProductInventoryCache(ProductInventory productInventory);

}

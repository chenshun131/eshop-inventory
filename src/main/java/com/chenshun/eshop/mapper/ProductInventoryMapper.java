package com.chenshun.eshop.mapper;

import com.chenshun.eshop.model.ProductInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * User: mew <p />
 * Time: 18/5/8 09:04  <p />
 * Version: V1.0  <p />
 * Description: 库存量 <p />
 */
@Mapper
public interface ProductInventoryMapper {

    /**
     * 更新库存量
     *
     * @param productInventory
     *         商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品id查询商品库存信息
     *
     * @param productId
     *         商品id
     * @return 商品库存信息
     */
    ProductInventory findProductInventory(@Param("productId") Integer productId);

}

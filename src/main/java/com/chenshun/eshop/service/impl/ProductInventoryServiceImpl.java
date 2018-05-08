package com.chenshun.eshop.service.impl;

import com.chenshun.eshop.dao.RedisDAO;
import com.chenshun.eshop.mapper.ProductInventoryMapper;
import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: mew <p />
 * Time: 18/5/8 09:18  <p />
 * Version: V1.0  <p />
 * Description: 产品库存 <p />
 */
@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        redisDAO.delete(String.format("product:inventory:%d", productInventory.getProductId()));
    }

    /**
     * 根据商品 id 查询商品库存
     *
     * @param productId
     *         商品id
     * @return 商品库存
     */
    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        redisDAO.set(String.format("product:inventory:%d", productInventory.getProductId()), productInventory.getInventoryCnt().toString());
    }

    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        String result = redisDAO.get(String.format("product:inventory:%d", productId));
        if (result != null && !"".equals(result)) {
            try {
                return new ProductInventory(productId, Long.valueOf(result));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

package com.chenshun.eshop.service.impl;

import com.chenshun.eshop.dao.RedisDAO;
import com.chenshun.eshop.mapper.ProductInventoryMapper;
import com.chenshun.eshop.model.ProductInventory;
import com.chenshun.eshop.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        logger.debug("===========日志===========: 已修改数据库中的库存，商品id=" + productInventory.getProductId() + ", 商品库存数量=" +
                productInventory.getInventoryCnt());
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = String.format("product:inventory:%d", productInventory.getProductId());
        redisDAO.delete(key);
        logger.debug("===========日志===========: 已删除redis中的缓存，key=" + key);
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

    /**
     * 设置商品库存的缓存
     *
     * @param productInventory
     */
    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = String.format("product:inventory:%d", productInventory.getProductId());
        redisDAO.set(key, productInventory.getInventoryCnt().toString());
        logger.debug("===========日志===========: 已更新商品库存的缓存，商品id=" + productInventory.getProductId() + ", 商品库存数量=" +
                productInventory.getInventoryCnt() + ", key=" + key);
    }

    /**
     * 获取商品库存的缓存
     *
     * @param productId
     * @return
     */
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

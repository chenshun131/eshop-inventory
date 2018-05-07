package com.chenshun.eshop.model;

/**
 * User: chenshun131 <p />
 * Time: 18/5/7 22:56  <p />
 * Version: V1.0  <p />
 * Description: 库存量 <p />
 */
public class ProductInventory {

    /** 商品id */
    private Integer productId;

    /** 库存数量 */
    private Long inventoryCnt;

    public ProductInventory() {
    }

    public ProductInventory(Integer productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getInventoryCnt() {
        return inventoryCnt;
    }

    public void setInventoryCnt(Long inventoryCnt) {
        this.inventoryCnt = inventoryCnt;
    }

}

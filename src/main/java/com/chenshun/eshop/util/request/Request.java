package com.chenshun.eshop.util.request;

/**
 * User: mew <p />
 * Time: 18/5/7 17:15  <p />
 * Version: V1.0  <p />
 * Description: 请求接口 <p />
 */
public interface Request {

    void process();

    /**
     * 获取产品 id
     *
     * @return
     */
    Integer getProductId();

    /**
     * 是否强制刷新
     *
     * @return
     */
    boolean isForceRefresh();

}

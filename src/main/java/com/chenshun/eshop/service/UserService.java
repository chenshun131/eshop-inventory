package com.chenshun.eshop.service;

import com.chenshun.eshop.model.User;

/**
 * User: mew <p />
 * Time: 18/5/7 09:47  <p />
 * Version: V1.0  <p />
 * Description: 用户 Service 接口 <p />
 */
public interface UserService {

    /**
     * 查询用户信息
     *
     * @return 返回用户信息
     */
    User findUserInfo();

    /**
     * 查询 Redis 中缓存的用户信息
     *
     * @return
     */
    User getCachedUserInfo();

}

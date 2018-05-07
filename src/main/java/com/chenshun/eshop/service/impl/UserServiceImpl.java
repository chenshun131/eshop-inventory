package com.chenshun.eshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chenshun.eshop.dao.RedisDAO;
import com.chenshun.eshop.mapper.UserMapper;
import com.chenshun.eshop.model.User;
import com.chenshun.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: mew <p />
 * Time: 18/5/7 09:50  <p />
 * Version: V1.0  <p />
 * Description: 用户 Service 的实现类 <p />
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCacheduserInfo() {
        redisDAO.set("cached_user_lisi", "{\"name\": \"lisi\", \"age\":28}");

        String userJSON = redisDAO.get("cached_user_lisi");
        return JSONObject.parseObject(userJSON, User.class);
    }

}

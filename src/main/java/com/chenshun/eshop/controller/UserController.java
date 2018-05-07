package com.chenshun.eshop.controller;

import com.chenshun.eshop.model.User;
import com.chenshun.eshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: mew <p />
 * Time: 18/5/7 10:12  <p />
 * Version: V1.0  <p />
 * Description: 用户 Controller 控制器 <p />
 */
@RequestMapping("user")
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("getUserInfo")
    public User getUserInfo() {
        logger.debug("打印测试信息");
        return userService.getCacheduserInfo();
    }

    @RequestMapping("getCacheUserInfo")
    public User getCacheUserInfo() {
        return userService.getCacheduserInfo();
    }

}

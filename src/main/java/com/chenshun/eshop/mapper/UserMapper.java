package com.chenshun.eshop.mapper;

import com.chenshun.eshop.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: mew <p />
 * Time: 18/5/7 08:57  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
@Mapper
public interface UserMapper {

    User findUserInfo();

}

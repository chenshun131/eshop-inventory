package com.chenshun.eshop.model;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 18/5/7 08:54  <p />
 * Version: V1.0  <p />
 * Description: 用户类 <p />
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7207480274160621476L;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}

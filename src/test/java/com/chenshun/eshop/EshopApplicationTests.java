package com.chenshun.eshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EshopApplicationTests {

    @Value("${common.redis.timeout}")
    private int timeout;

    @Value("${common.threadpool.threadcount}")
    private int threadcount;

    @Value("${common.redis.cluster}")
    private List<Map<String, Integer>> list;

    @Test
    public void contextLoads() {
        System.out.println("+++++++++++++++++++++++++");
        System.out.println("timeout=" + timeout);
        System.out.println("+++++++++++++++++++++++++");
        System.out.println("threadcount=" + threadcount);
        System.out.println("+++++++++++++++++++++++++");
        System.out.println(list.size());
        System.out.println("+++++++++++++++++++++++++");
    }

}

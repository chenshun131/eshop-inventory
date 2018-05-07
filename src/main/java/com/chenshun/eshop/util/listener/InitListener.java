package com.chenshun.eshop.util.listener;

import com.chenshun.eshop.util.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * User: mew <p />
 * Time: 18/5/7 17:10  <p />
 * Version: V1.0  <p />
 * Description: 初始化系统监听器 <p />
 */
@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化工作线程池和内存队列
        RequestProcessorThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}

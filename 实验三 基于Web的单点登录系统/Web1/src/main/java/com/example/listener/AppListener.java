package com.example.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    // 在此初始化WebApp
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器启动！");
    }

    // 在此清理WebApp,例如关闭数据库连接池等:
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("服务器关闭！");
    }

}
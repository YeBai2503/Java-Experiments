package com.ssoserver.listener;

import com.ssoserver.model.DB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppListener implements ServletContextListener {
    // 在此初始化WebApp,加载 MySQL JDBC 驱动程序
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器启动！");
        try {
            DB.connect();
        } catch (SQLException e) {
            throw new RuntimeException("!!!!Mysql驱动加载错误");
        }
    }

    // 在此清理WebApp,例如关闭数据库连接池等:
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("服务器关闭！");
    }

}

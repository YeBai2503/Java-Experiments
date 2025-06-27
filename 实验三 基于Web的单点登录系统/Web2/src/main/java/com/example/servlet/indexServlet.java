package com.example.servlet;

import com.example.model.OnlineBO;
import com.example.tool.DB;
import com.example.tool.DB2;
import com.example.model.User;
import com.example.tool.GetTime;
import com.example.tool.JwtBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// 定义 Servlet 映射
@WebServlet("/index") // 访问 /home 的请求将被这个 Servlet 处理
public class indexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cookie[] cookies = request.getCookies();
            String token = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("SSO_Cookie".equals(cookie.getName())) {
                        System.out.println("成功找到cookie！！！");
                        token = JwtBuilder.parseJwt(cookie.getValue());
                        break;
                    }
                }
            }
            if (token != null) {
                User user = DB.queryUser(Integer.parseInt(token)).get(0); // 根据 token 查询用户信息
                request.setAttribute("id", user.getId());
                request.setAttribute("name", user.getName());
                request.setAttribute("email", user.getEmail());
                if(user.getIsManager()==1) request.setAttribute("manager", "是");
                else request.setAttribute("manager", "否");
                request.setAttribute("time", GetTime.get());
                //在线表格
                OnlineBO onlineBO = new OnlineBO();
                onlineBO.setOnline(user.getId(), "Web2");
                request.setAttribute("onlineBO", onlineBO);
            } else {
                System.out.println("！！！未找到cookie");
            }
            System.out.println("成功进入主页！");
            // 转发到 JSP 页面
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常以帮助调试
            throw new ServletException("发生错误，无法处理请求", e); // 包装并抛出异常
        }
    }
}

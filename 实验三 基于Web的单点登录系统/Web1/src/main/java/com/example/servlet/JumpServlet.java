package com.example.servlet;

import com.example.tool.JwtBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Jump")
public class JumpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求中获取参数
        String web = request.getParameter("webInfo");

        // 根据获取的信息做适当处理，例如打印或重定向
        System.out.println("JumpServlet: 跳转: " + web);

        // 尝试从cookie中获取token
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SSO_Cookie".equals(cookie.getName())) {
                    System.out.println("JumpServlet: 成功找到cookie！！！");
                    token = JwtBuilder.parseJwt(cookie.getValue());
                    break;
                }
            }
        }
        if (token != null) {
            // 这里可以进行重定向或其他操作
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("你点击了: " + web);
            int id=Integer.parseInt(token);
            //HistoryCreate.create(id,web);
            response.sendRedirect(web != null ? web : "LogUser.jsp");
        }
        else {
            System.out.println("！！！！JumpServlet: 跳转失败，没有找到cookie！！！");
        }
    }
}

package com.example.servlet;

import com.example.tool.DB;
import com.example.tool.JwtBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        // 查找要删除的 Cookie
        Cookie cookieToDelete = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 要删除的 Cookie 的名称是 "SSO_Cookie"
                if ("SSO_Cookie".equals(cookie.getName())) {
                    cookieToDelete = cookie;
                    break;
                }
            }
        }

        String token = JwtBuilder.parseJwt(cookieToDelete.getValue());
        request.setAttribute("id", Integer.parseInt(token));
        request.setAttribute("name", DB.queryUser(Integer.parseInt(token)).get(0).getName());
        request.setAttribute("order", "登出成功");


        if (cookieToDelete != null) {
            // 设置 Cookie 的最大生存时间为 0 即可删除
            cookieToDelete.setValue(null); // 清空值
            cookieToDelete.setMaxAge(0); // 设置生存时间为 0
            cookieToDelete.setPath("/"); // 设置路径, 确保与原来的 Cookie 一致
            response.addCookie(cookieToDelete); // 重新添加到响应中，删除 cookie
        }

        request.getRequestDispatcher("/exit.jsp").forward(request, response);
        // 可选择性地重定向到登录页面或首页
        //response.sendRedirect("exit.jsp");

    }
}

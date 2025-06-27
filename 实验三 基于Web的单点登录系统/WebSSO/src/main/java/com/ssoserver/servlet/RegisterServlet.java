package com.ssoserver.servlet;
import com.ssoserver.model.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求字符编码为 UTF-8
        int id = Integer.parseInt(request.getParameter("id")); // 从表单获取的id
        String username = request.getParameter("username"); // 从表单获取的用户名
        String password = request.getParameter("password"); // 从表单获取的密码
        String email = request.getParameter("email"); // 从表单获取的邮箱
        DB.createUser(id, username, password, email,0,0);
        response.sendRedirect("http://localhost:8080/WebSSO_war_exploded/");
    }
}

package com.ssoserver.servlet;
import com.ssoserver.command.CreateOnlineCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/online")
public class OnlineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // 读取 POST 请求体中的数据
        String id = request.getParameter("id"); // 获取 'id' 参数
        // 根据 ID 进行其他操作
        CreateOnlineCommand command = new CreateOnlineCommand();
        command.execute(Integer.parseInt(id));
        // 设置响应状态
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("在线模式: "+id);
    }
}
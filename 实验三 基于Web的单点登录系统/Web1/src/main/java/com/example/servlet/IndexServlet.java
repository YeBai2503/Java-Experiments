package com.example.servlet;
import com.example.model.OnlineBO;
import com.example.tool.DB;
import com.example.tool.DB2;
import com.example.model.User;
import com.example.tool.GetTime;
import com.example.tool.JwtBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {
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
                //初始化个人信息
                request.setAttribute("id", user.getId());
                request.setAttribute("name", user.getName());
                request.setAttribute("email", user.getEmail());
                if(user.getIsManager()==1) request.setAttribute("manager", "是");
                else request.setAttribute("manager", "否");

                //在线表格
                OnlineBO onlineBO = new OnlineBO();
                onlineBO.setOnline(user.getId(),"Web1");
                request.setAttribute("onlineBO", onlineBO);
//                DB2.setTableName("onlineUser");
//                ArrayList<User> onlineList = DB2.queryUser(0,"Web1");
//                //把自己也加上
//                //记得改网站
//                int isHaven=0;//判断当前数据库是否有当前用户，如果没有，则自动添加自己在线
//                for (User userOnline : onlineList) {
//                    if (userOnline.getId() == user.getId() && userOnline.getWeb().equals("Web1")) {
//                        isHaven=1;
//                        break;
//                    }
//                }
//                if (isHaven==0)
//                {
//                    onlineList.add(new User(user.getId(), user.getName(), user.getEmail(), GetTime.get(),"Web1"));
//                    System.out.println("1111111自动添加自己在线");
//                }
//                // 将数据放入请求作用域
//                request.setAttribute("onlineList", onlineList);
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

